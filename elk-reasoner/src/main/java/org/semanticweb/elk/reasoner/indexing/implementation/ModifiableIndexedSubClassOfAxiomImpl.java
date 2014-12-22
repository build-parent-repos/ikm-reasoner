/*
 * #%L
 * ELK Reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.reasoner.indexing.implementation;

import org.semanticweb.elk.reasoner.indexing.conversion.ElkUnexpectedIndexingException;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableIndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableIndexedSubClassOfAxiom;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableOntologyIndex;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedAxiomVisitor;
import org.semanticweb.elk.reasoner.saturation.rules.subsumers.SuperClassFromSubClassRule;

/**
 * Implements {@link ModifiableIndexedSubClassOfAxiom}
 * 
 * @author "Yevgeny Kazakov"
 */
class ModifiableIndexedSubClassOfAxiomImpl extends ModifiableIndexedAxiomImpl
		implements ModifiableIndexedSubClassOfAxiom {

	private final ModifiableIndexedClassExpression subClass_, superClass_;

	ModifiableIndexedSubClassOfAxiomImpl(
			ModifiableIndexedClassExpression subClass,
			ModifiableIndexedClassExpression superClass) {
		this.subClass_ = subClass;
		this.superClass_ = superClass;
	}

	@Override
	public final ModifiableIndexedClassExpression getSubClass() {
		return this.subClass_;
	}

	@Override
	public final ModifiableIndexedClassExpression getSuperClass() {
		return this.superClass_;
	}

	@Override
	public final String toStringStructural() {
		return "SubClassOf(" + this.subClass_ + ' ' + this.superClass_ + ')';
	}

	@Override
	public final boolean updateOccurrenceNumbers(ModifiableOntologyIndex index,
			int increment) {
		boolean success = true;
		if (increment > 0) {
			int added = 0;
			for (int i = 0; i < increment; i++) {
				if (addOnce(index))
					added++;
				else {
					success = false;
					break;
				}
			}
			if (!success) {
				// revert the changes
				for (; added > 0; added--) {
					if (!removeOnce(index))
						throw new ElkUnexpectedIndexingException(this);
				}
			}
		} else {
			// increment <= 0
			int removed = 0;
			for (int i = 0; i < -increment; i++) {
				if (removeOnce(index))
					removed++;
				else {
					success = false;
					break;
				}
			}
			if (!success) {
				// revert the changes
				for (; removed > 0; removed--) {
					if (!addOnce(index))
						throw new ElkUnexpectedIndexingException(this);
				}
			}
		}
		return success;
	}

	boolean addOnce(ModifiableOntologyIndex index) {
		return SuperClassFromSubClassRule.addRuleFor(this, index);
	}

	boolean removeOnce(ModifiableOntologyIndex index) {
		return SuperClassFromSubClassRule.removeRuleFor(this, index);
	}

	@Override
	public final <O> O accept(IndexedAxiomVisitor<O> visitor) {
		return visitor.visit(this);
	}

}