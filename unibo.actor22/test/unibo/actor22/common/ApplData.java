package unibo.actor22.common;

import it.unibo.kactor.ApplMessage;
import it.unibo.kactor.ApplMessageType;
import it.unibo.kactor.IApplMessage;
import unibo.actor22.Qak22Util;
import unibo.actor22comm.ProtocolType;
import unibo.actor22comm.utils.ColorsOut;

public class ApplData {
	public static final String raspAddr       = "localhost";

	
	public static final String ledName        = "led";
	public static final String sonarName      = "sonar";
	public static final String controllerName = "controller";
	public static final String observerName   = "observer";

	public static final String comdLedon   = "turnOn";
	public static final String comdLedoff  = "turnOff";
	public static final String reqLedState = "getState";
	public static final String reqDistance = "getDistance";

	public static final String cmdActivate  = "activate";
	public static final String cmdDectivate = "deactivate";

	public static final String evEndWork    = "endWork";
	public static final String evDistance   = "distance";

	public static final int ctxPort          = 8018;
	public static final ProtocolType protocol= ProtocolType.tcp;
	
	public static final IApplMessage turnOnLed    = Qak22Util.buildDispatch(controllerName, "cmd", comdLedon,   ledName);
	public static final IApplMessage turnOffLed   = Qak22Util.buildDispatch(controllerName, "cmd", comdLedoff,  ledName);
	
	public static final  IApplMessage activateCrtl    = Qak22Util.buildDispatch("main", "cmd", cmdActivate, controllerName);
	public static final  IApplMessage activateSonar   = Qak22Util.buildDispatch(controllerName, "cmd", cmdActivate, sonarName);
	public static final  IApplMessage deactivateSonar = Qak22Util.buildDispatch(controllerName, "cmd", cmdDectivate, sonarName);
	public static final  IApplMessage askDistance     = Qak22Util.buildRequest(controllerName, "ask", reqDistance, sonarName);
	
	public static final  IApplMessage endWorkEvent  = Qak22Util.buildEvent(controllerName, evEndWork, evEndWork );
	
	

	public static final String reqSonarDistance = "getDistance";
	public static final String reqSonarActive = "isActive";

	public static final IApplMessage getLedState = buildRequest(controllerName, "cmd", reqSonarDistance, sonarName);

	public static final IApplMessage isActiveSonar    = buildRequest(controllerName, "cmd", reqSonarActive,   sonarName);
	public static final IApplMessage getSonarDistance = buildRequest(controllerName, "cmd", reqSonarDistance, sonarName);

	
//String MSGID, String MSGTYPE, String SENDER, String RECEIVER, String CONTENT, String SEQNUM
	private static int msgNum = 0;

	public static IApplMessage buildDispatch(String sender, String msgId, String payload, String dest) {
		try {
			return new ApplMessage(msgId, ApplMessageType.dispatch.toString(), sender, dest, payload, "" + (msgNum++));
		} catch (Exception e) {
			ColorsOut.outerr("buildDispatch ERROR:" + e.getMessage());
			return null;
		}
	}

	public static IApplMessage buildRequest(String sender, String msgId, String payload, String dest) {
		try {
			return new ApplMessage(msgId, ApplMessageType.request.toString(), sender, dest, payload, "" + (msgNum++));
		} catch (Exception e) {
			ColorsOut.outerr("buildRequest ERROR:" + e.getMessage());
			return null;
		}
	}

	public static IApplMessage buildReply(String sender, String msgId, String payload, String dest) {
		try {
			return new ApplMessage(msgId, ApplMessageType.reply.toString(), sender, dest, payload, "" + (msgNum++));
		} catch (Exception e) {
			ColorsOut.outerr("buildReply ERROR:" + e.getMessage());
			return null;
		}
	}

	public static IApplMessage buildEvent(String emitter, String msgId, String payload) {
		try {
			return new ApplMessage(msgId, ApplMessageType.event.toString(), emitter, "ANY", payload, "" + (msgNum++));
		} catch (Exception e) {
			ColorsOut.outerr("buildEvent ERROR:" + e.getMessage());
			return null;
		}
	}

	public static IApplMessage prepareReply(IApplMessage requestMsg, String answer) {
		String sender = requestMsg.msgSender();
		String receiver = requestMsg.msgReceiver();
		String reqId = requestMsg.msgId();
		IApplMessage reply = null;
		if (requestMsg.isRequest()) { // DEFENSIVE
			// The msgId of the reply must be the id of the request !!!!
			reply = buildReply(receiver, reqId, answer, sender);
		} else {
			ColorsOut.outerr("Utils | prepareReply ERROR: message not a request");
		}
		return reply;
	}
	
	
}
