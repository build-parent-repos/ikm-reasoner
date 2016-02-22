package org.semanticweb.elk.reasoner.taxonomy.model;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2016 Department of Computer Science, University of Oxford
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

import java.util.Collection;

import org.semanticweb.elk.owl.interfaces.ElkEntity;

/**
 * Generic instance taxonomy that can be modified.
 * 
 * @author Peter Skocovsky
 *
 * @param <T>
 *            The type of members of the type nodes in this taxonomy.
 * @param <I>
 *            The type of members of the instance nodes in this taxonomy.
 * @param <TN>
 *            The type of type nodes in this taxonomy.
 * @param <IN>
 *            The type of instance nodes in this taxonomy.
 */
public interface UpdateableGenericInstanceTaxonomy<T extends ElkEntity, I extends ElkEntity, TN extends UpdateableGenericTypeNode<T, I, TN, IN>, IN extends UpdateableGenericInstanceNode<T, I, TN, IN>>
		extends UpdateableGenericTaxonomy<T, TN>,
		GenericInstanceTaxonomy<T, I, TN, IN> {

	IN getCreateInstanceNode(Collection<? extends I> instances);
	
	boolean setCreateDirectTypes(IN instanceNode,
			Iterable<? extends Collection<? extends T>> typeSets);
	
	boolean removeDirectTypes(IN instanceNode);

	/**
	 * Removes the instance node containing the specified member from the
	 * taxonomy.
	 * 
	 * @param member
	 *            The member whose instance node should be removed.
	 * @return <code>true</code> if and only if some node was removed.
	 */
	boolean removeInstanceNode(I instance);

}
