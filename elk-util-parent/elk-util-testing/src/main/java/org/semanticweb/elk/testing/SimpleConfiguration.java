/*
 * #%L
 * ELK Utilities for Testing
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.semanticweb.elk.testing;

import java.util.List;

import org.semanticweb.elk.testing.PolySuite.Configuration;

/**
 * @author Pavel Klinov
 *
 *         pavel.klinov@uni-ulm.de
 * @author Peter Skocovsky
 */
public class SimpleConfiguration implements Configuration {

	private final List<? extends TestManifest<?>> manifests;

	SimpleConfiguration(final List<? extends TestManifest<?>> manifests) {
		this.manifests = manifests;
	}

	@Override
	public int size() {
		return manifests.size();
	}

	@Override
	public Object getTestValue(final int index) {
		return manifests.get(index);
	}

	@Override
	public String getTestName(int index) {
		return "test" + manifests.get(index).getName();
	}

}
