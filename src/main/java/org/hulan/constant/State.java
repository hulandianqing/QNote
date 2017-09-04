package org.hulan.constant;

import com.alibaba.fastjson.JSON;

/**
 * 功能描述：
 * 时间：2017/6/11 12:18
 * @author ：zhaokuiqiang
 */
public class State {
	public static final StateWrapper SUCCESS = new StateWrapper("success","成功");
	public static final StateWrapper ERROR = new StateWrapper("error","失败");
	
	public static final StateWrapper OPERATOR_NOT_FOUNT = new StateWrapper("101","用户不存在");;
	public static final StateWrapper OPERATOR_PASSWORD_ISNULL = new StateWrapper("102","密码为空");;
	public static final StateWrapper OPERATOR_PASSWORD_ERROR = new StateWrapper("103","密码错误");;
	public static final StateWrapper OPERATOR_STATUS_ERROR = new StateWrapper("104","状态异常");;
	public static final StateWrapper JUMP = new StateWrapper("9999","");;
	
	public static class StateWrapper{
		String state = null;
		String message = null;
		public StateWrapper(String state,String message) {
			this.state = state;
			this.message = message;
		}
		
		public String getState() {
			return state;
		}
		
		public String getMessage() {
			return message;
		}
		
		@Override
		public String toString() {
			return JSON.toJSONString(this);
		}
	}
}
