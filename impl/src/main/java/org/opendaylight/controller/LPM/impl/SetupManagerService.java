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

public interface SetupManagerService {

	public boolean setupPath(Short src,Long olddst, Long dst, Integer connectNe);

	public boolean startSetupPath(Short srcRRU1,Short srcRRU2,Long dstBBU1,Long dstBBU2,Integer connectNe);

	public boolean startSetupVodPath(Short srcRRU1,Short srcRRU2,Long dstBBU1,Long dstBBU2,Integer connectNe,Short connectWss);
}