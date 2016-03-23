package com.kuaiba.site.core.exception;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuaiba.site.core.thread.ThreadPool;
import com.kuaiba.site.db.entity.SiteResponse;
import com.kuaiba.site.db.entity.Track;
import com.kuaiba.site.service.TrackService;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Resource
	private TrackService trackService;
	
	/**
	 * 业务异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ BaseException.class })
	public @ResponseBody SiteResponse handle(BaseException e) {
		track(e);
		return new SiteResponse(e);
	}
	
	/**
	 * 运行时异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ BaseRuntimeException.class })
	public @ResponseBody SiteResponse handle(BaseRuntimeException e) {
		track(e);
		return new SiteResponse(e);
	}
	
	/**
	 * 未知错误
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	public @ResponseBody SiteResponse handle(Exception e) {
		track(e);
		SecurityException ex = new SecurityException(ErrorIDs.UNKNOWN, "未知错误");
		return new SiteResponse(ex);
	}
	
	private void track(Exception e) {
		ThreadPool.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					Track record = new Track();
					record.setName(e.getClass().getName());
					record.setDescription(e.getMessage());
					record.setState((byte)1);
					trackService.add(record);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

}