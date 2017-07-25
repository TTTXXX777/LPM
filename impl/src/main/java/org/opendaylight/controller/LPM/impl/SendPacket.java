/*
 * Copyright © 2017 BNI, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.LPM.impl;

import org.opendaylight.controller.ted.impl.LinkPropertyService;
import org.opendaylight.controller.ted.impl.LinkProperty;

import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.BBUConfigInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.BBUConfigInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.RRUConfigInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.RRUConfigInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.SWConfigInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.SWConfigInputBuilder;

import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setbbuconfig.rev170705.PacketSetBbuConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setrruconfig.rev170705.PacketSetRruConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setswconfig.rev170705.PacketSetSwConfigService;

import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setbbuconfig.rev170705.PacketSetBbuConfigInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setrruconfig.rev170705.PacketSetRruConfigInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.setswconfig.rev170705.PacketSetSwConfigInputBuilder;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.LPM.impl.InstanceIdentifierUtils;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import java.util.*;
import java.lang.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendPacket {
	private static final Logger LOG = LoggerFactory.getLogger(SendPacket.class);
	public static PacketSetRruConfigService packetSetRruConfigService = null;
	public static PacketSetBbuConfigService packetSetBbuConfigService = null;
	public static PacketSetSwConfigService packetSetSwConfigService = null;

	public static LinkPropertyService linkPropertyService; 

	public  SendPacket(LinkPropertyService linkPropertyService) {
		this.linkPropertyService = linkPropertyService;
	}

	public static void sendBBUConfig(Long id, Short switchStatus){
		LOG.info("===============sendBBUConfig begin=============");
		PacketSetBbuConfigInputBuilder bbuConfigBuilder = new PacketSetBbuConfigInputBuilder();		

		java.lang.Short id1 = new Short(id.shortValue());
		Integer cn = 0;
		Short re = 0;
		
		LOG.info("===============noderef=============" + linkPropertyService.getNodeMapIngress());
		NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIngress().get(id1);
		LOG.info("===============noderef=============" + nodeRef);
		LOG.info("===============noderef=============" + nodeRef.getValue());
		InstanceIdentifier<Node> egressNodePath = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());	
		LOG.info("===============noderef=============" + egressNodePath);	
		bbuConfigBuilder.setNode(new NodeRef(egressNodePath));
		bbuConfigBuilder.setChannelNumber(cn);
		bbuConfigBuilder.setSwitchStatus(switchStatus);
		bbuConfigBuilder.setReserve(re);

		packetSetBbuConfigService.packetSetBbuConfig(bbuConfigBuilder.build());
		//if ()
		LOG.info("===============sendbuild-------------------{}"+bbuConfigBuilder);
		LOG.info("===============sendBBUConfig end  ==================");
	}

	public static void sendRRUConfig(Short id, Long target_bbu_id){
		LOG.info("===============sendRRUConfig begin=============");
		PacketSetRruConfigInputBuilder rruConfigBuilder = new PacketSetRruConfigInputBuilder();		
		java.lang.Short target_bbu_id1 = new Short(target_bbu_id.shortValue());
		LOG.info("============== 7777777777777777777777target_bbu_id =================="+target_bbu_id);
		Short cn = 0;
		Integer bk = 0;
		
		NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIngress().get(id);
		InstanceIdentifier<Node> egressNodePath = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());		
		rruConfigBuilder.setNode(new NodeRef(egressNodePath));
		rruConfigBuilder.setChannelNumber(cn); 
		rruConfigBuilder.setBlank(bk);
		rruConfigBuilder.setTargetBbuId(target_bbu_id1);

		packetSetRruConfigService.packetSetRruConfig(rruConfigBuilder.build());
		//if ()
		LOG.info("===============sendbuild-------------------{}"+rruConfigBuilder);
		LOG.info("===============sendRRUConfig end  ==================");
	}

	public static void sendSWConfig(Integer id, Long dst1){
		LOG.info("===============sendSWConfig begin=============");
		PacketSetSwConfigInputBuilder swConfigBuilder = new PacketSetSwConfigInputBuilder();
		java.lang.Short dst = new Short(dst1.shortValue()); 
		LOG.info("============== 7777777777777777777777dst =================="+dst);
		//Short dst_test = 4;			
		java.lang.Short id1 = new Short(id.shortValue());
		LOG.info("============== 7777777777777777777777id1 =================="+id1);
		
		List<Integer> outputPort = new ArrayList<Integer>();
		List<Integer> inputPort = new ArrayList<Integer>();

		for(Map<Short,List<Integer>> physicallink: linkPropertyService.getPhysicalTopo()) {
			for (Short dev_id: physicallink.keySet()) {
				if(dst.equals(dev_id)) {//not "==" , is "a.equals(b)"
					outputPort.add(physicallink.get(dev_id).get(2));
					LOG.info("============== 7777777777777777777777outputPort =================="+outputPort);
				}
			}
		}

		for(Map<Short,List<Integer>> physicallink: linkPropertyService.getPhysicalTopo()) {
			for(Short dev_id: physicallink.keySet()) {
				Short low = 0;
				Short high = 3;
				if (dev_id>=low && dev_id<=high) {
					inputPort.add(physicallink.get(dev_id).get(2));
					LOG.info("============== 7777777777777777777777inputPort =================="+inputPort);
				}
			}
		}
		Long input = (inputPort.get(0).longValue() << 16) | (inputPort.get(1).longValue());
		Long output = (outputPort.get(0).longValue() << 16) | (outputPort.get(1).longValue());
		NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIngress().get(id1);
		InstanceIdentifier<Node> egressNodePath = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());		
		swConfigBuilder.setNode(new NodeRef(egressNodePath));
		swConfigBuilder.setOutputPort(output);
		swConfigBuilder.setInputPort(input);

		packetSetSwConfigService.packetSetSwConfig(swConfigBuilder.build());
		//if ()
		LOG.info("===============sendbuild-------------------{}"+swConfigBuilder);
		LOG.info("===============sendSWConfig end  ==================");
	}

		public static void sendWSSConfig(Short id){
		LOG.info("===============sendWSSConfig begin=============");
		PacketSetSwConfigInputBuilder wssConfigBuilder = new PacketSetSwConfigInputBuilder();			
		LOG.info("============== 7777777777777777777777id =================="+id);
		

		List<Long> list= new ArrayList<Long>();
		for(int i =1;i<10;i++) {
			list.add((long)i);
		}

		int rm1 = (int)(Math.random()*9);
		Long temp1 = list.get(rm1);
		list.remove(rm1);

		int rm2 = (int)(Math.random()*8);
		Long temp2 = list.get(rm2);
		list.remove(rm2);
		Long input = (temp1 << 16) | (0x00000005l);
		Long output = (temp2 << 16) | (0x00000005l);

		NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIngress().get(id);
		InstanceIdentifier<Node> egressNodePath = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());		
		wssConfigBuilder.setNode(new NodeRef(egressNodePath));
		wssConfigBuilder.setOutputPort(output);
		wssConfigBuilder.setInputPort(input);

		packetSetSwConfigService.packetSetSwConfig(wssConfigBuilder.build());
		LOG.info("===============sendbuild-------------------{}"+wssConfigBuilder);
		LOG.info("===============sendWSSConfig end  ==================");
	}
}
	
	
