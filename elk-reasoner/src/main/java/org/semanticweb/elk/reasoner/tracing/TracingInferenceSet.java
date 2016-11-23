package org.semanticweb.elk.reasoner.tracing;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2015 Department of Computer Science, University of Oxford
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

/**
 * 
 * An object which can be used to retrieve {@link TracingInference}s producing a
 * given {@link Conclusion}.
 * 
 * @author "Yevgeny Kazakov"
 */
public interface TracingInferenceSet {

	/**
	 * @param conclusion
	 * @return all {@link TracingInference}s stored in this
	 *         {@link TracingInferenceSet} producing the given
	 *         {@link Conclusion}
	 */
	public Iterable<? extends TracingInference> getInferences(
			Conclusion conclusion);

	public void add(ChangeListener listener);

	public void remove(ChangeListener listener);

	/**
	 * A listener to monitor if inferences for axioms have changed
	 * 
	 * @author Yevgeny Kazakov
	 */
	public interface ChangeListener {

		/**
		 * called when the inferences for some conclusion may have changed,
		 * i.e., calling {@link TracingInferenceSet#getInferences(Conclusion)}
		 * for the same conclusion may produce a different result
		 */
		void inferencesChanged();

	}

}