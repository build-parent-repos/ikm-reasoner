/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.inferences;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2013 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectProperty;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedPropertyChain;
import org.semanticweb.elk.reasoner.saturation.IndexedContextRoot;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.ForwardLinkImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.BackwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.ForwardLink;
import org.semanticweb.elk.reasoner.saturation.inferences.properties.SubPropertyChain;
import org.semanticweb.elk.reasoner.saturation.inferences.properties.SubPropertyChainImpl;
import org.semanticweb.elk.reasoner.saturation.inferences.visitors.BackwardLinkInferenceVisitor;

/**
 * A {@link BackwardLink} that is obtained by reversing a given
 * {@link ForwardLink}.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class ReversedForwardLink extends AbstractBackwardLinkInference {

	private final IndexedPropertyChain forwardChain_;

	/**
	 * 
	 */
	public ReversedForwardLink(ForwardLink premise,
			IndexedObjectProperty superRelation) {
		super(premise.getTarget(), superRelation, premise.getConclusionRoot());
		this.forwardChain_ = premise.getForwardChain();
	}

	public IndexedPropertyChain getForwardChain() {
		return forwardChain_;
	}

	@Override
	public IndexedContextRoot getInferenceRoot() {
		return getOriginRoot();
	}

	public ForwardLink getFirstPremise() {
		return new ForwardLinkImpl<IndexedPropertyChain>(getInferenceRoot(),
				forwardChain_, getConclusionRoot());
	}

	public SubPropertyChain getSecondPremise() {
		return new SubPropertyChainImpl(forwardChain_, getBackwardRelation());
	}

	@Override
	public <I, O> O accept(BackwardLinkInferenceVisitor<I, O> visitor,
			I parameter) {
		return visitor.visit(this, parameter);
	}

}
