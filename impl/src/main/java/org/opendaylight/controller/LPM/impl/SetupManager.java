/*
 * Copyright © 2017 BNI, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.LPM.impl;


import java.util.*;
import java.lang.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetupManager extends Thread implements SetupManagerService {

	@Override
	public boolean setupPath(Short src,Long olddst, Long dst, Integer connectNe){
		Short oldSwitchStatus = 0;
		Short switchStatus = 1; 
		
		SendPacket.sendRRUConfig(src, dst); 
		SendPacket.sendBBUConfig(dst, switchStatus);
		SendPacket.sendBBUConfig(olddst, oldSwitchStatus);
		SendPacket.sendSWConfig(connectNe, dst);
		return true;
	}

	@Override
	public boolean startSetupPath(Short srcRRU1,Short srcRRU2,Long dstBBU1,Long dstBBU2,Integer connectNe){
		Short switchStatus = 1;
		SendPacket.sendRRUConfig(srcRRU1,dstBBU1);
		SendPacket.sendBBUConfig(dstBBU1, switchStatus);
		SendPacket.sendSWConfig(connectNe, dstBBU1);

		SendPacket.sendRRUConfig(srcRRU2,dstBBU2);
		SendPacket.sendBBUConfig(dstBBU2, switchStatus);
		SendPacket.sendSWConfig(connectNe, dstBBU2);
		return true;
	}

	@Override
	public boolean startSetupVodPath(Short srcRRU1,Short srcRRU2,Long dstBBU1,Long dstBBU2,Integer connectNe,Short connectWss){
		Short switchStatus = 1;
		SendPacket.sendRRUConfig(srcRRU1,dstBBU1);
		SendPacket.sendBBUConfig(dstBBU1, switchStatus);
		SendPacket.sendSWConfig(connectNe, dstBBU1);

		SendPacket.sendRRUConfig(srcRRU2,dstBBU2);
		SendPacket.sendBBUConfig(dstBBU2, switchStatus);
		SendPacket.sendSWConfig(connectNe, dstBBU2);

		SendPacket.sendWSSConfig(connectWss);
		return true;
	}
	
}
