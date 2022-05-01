package unibo.actor22.common;

import it.unibo.kactor.IApplMessage;
import it.unibo.kactor.MsgUtil;
import it.unibo.radarSystem22.domain.DeviceFactory;
import unibo.actor22.QakActor22;
import it.unibo.radarSystem22.domain.interfaces.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

public class SonarActor extends QakActor22{
private ISonar sonar;

	public SonarActor(String name) {
		super(name);
		sonar = DeviceFactory.createSonar();
	}

	@Override
	protected void handleMsg(IApplMessage msg) {
		CommUtils.aboutThreads(getName()  + " |  Before doJob - ");
		ColorsOut.out( getName()  + " | doJob " + msg, ColorsOut.BLUE);
		if( msg.isRequest() ) elabRequest(msg);
		else elabCmd(msg);
	}

	protected void elabCmd(IApplMessage msg) {
		ColorsOut.out( getName()  + " | Cmd " + msg, ColorsOut.BLUE);
		String msgCmd = msg.msgContent();
		switch( msgCmd ) {
			case ApplData.cmdActivate    : sonar.activate();break;
			case ApplData.cmdDectivate : sonar.deactivate();break;
 			default: ColorsOut.outerr(getName()  + " | unknown " + msgCmd);
		}
	}

	protected void elabRequest(IApplMessage msg) {
		ColorsOut.out( getName()  + " | Request " + msg, ColorsOut.BLUE);
		String msgReq = msg.msgContent();
		//ColorsOut.out( getName()  + " | elabRequest " + msg, ColorsOut.CYAN);
		switch( msgReq ) {
			case ApplData.reqSonarActive  :{
				boolean b = sonar.isActive();
				IApplMessage reply = MsgUtil.buildReply(getName(), "sonarState", ""+b, msg.msgSender());
				ColorsOut.out( getName()  + " | sendAnswer reply=" + reply, ColorsOut.CYAN);
 				sendReply( msg,reply );
				break;
			}
			case ApplData.reqSonarDistance  :{
				int d = sonar.getDistance().getVal();
				IApplMessage reply = MsgUtil.buildReply(getName(), "distance", ""+d, msg.msgSender()); //"distance"
				ColorsOut.out( getName()  + " | sendAnswer reply=" + reply, ColorsOut.CYAN);
				sendReply( msg,reply );
				break;
			}
 			default: ColorsOut.outerr(getName()  + " | elabRequest unknown " + msgReq);
		}
	}
	

}