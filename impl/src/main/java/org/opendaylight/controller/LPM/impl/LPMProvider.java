/*
 * Copyright © 2017 BNI, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.LPM.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;

import org.opendaylight.controller.ted.impl.LinkPropertyService;
import org.opendaylight.controller.ted.impl.LinkProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LPMProvider {

    private static final Logger LOG = LoggerFactory.getLogger(LPMProvider.class);

    private final DataBroker dataBroker;
    private final RpcProviderRegistry rpcRegistry;
    private final NotificationPublishService notificationService;
    private final LinkPropertyService linkPropertyService;

    public LPMProvider(final DataBroker dataBroker, 
        final RpcProviderRegistry rpcRegistry, 
        final NotificationPublishService notificationService,
        final LinkPropertyService linkPropertyService) {
        this.dataBroker = dataBroker;       
        this.rpcRegistry = rpcRegistry;
        this.notificationService = notificationService;
        this.linkPropertyService = linkPropertyService;

    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() {
        LOG.info("LPMProvider Session Initiated");
        new SendPacket(linkPropertyService);
    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("LPMProvider Closed");
    }
}