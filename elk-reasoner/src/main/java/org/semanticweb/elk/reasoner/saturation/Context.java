/*
 * #%L
 * elk-reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Oxford University Computing Laboratory
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
package org.semanticweb.elk.reasoner.saturation;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.semanticweb.elk.reasoner.indexing.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.IndexedObjectProperty;
import org.semanticweb.elk.util.ArrayHashSet;
import org.semanticweb.elk.util.HashSetMultimap;
import org.semanticweb.elk.util.Multimap;
import org.semanticweb.elk.util.Pair;

/**
 * @author Frantisek Simancik
 * 
 */
public class Context {
	final IndexedClassExpression root;
	final Queue<IndexedClassExpression> classQueue;
	final Queue<Pair<IndexedObjectProperty, Context>> linkQueue;
	final Queue<Pair<IndexedObjectProperty, IndexedClassExpression>> propagationQueue;
	final Set<IndexedClassExpression> derived;
	final Multimap<IndexedObjectProperty, Context> linksByObjectProperty;
	final Multimap<IndexedObjectProperty, IndexedClassExpression> propagationsByObjectProperty;
	// Context is active iff one of its queues is not empty or it is being
	// processed
	private AtomicBoolean isActive;

	public Context(IndexedClassExpression root) {
		this.root = root;
		this.classQueue = new ConcurrentLinkedQueue<IndexedClassExpression>();
		this.linkQueue = 
			new ConcurrentLinkedQueue<Pair<IndexedObjectProperty, Context>>();
		this.propagationQueue = 
			new ConcurrentLinkedQueue<Pair<IndexedObjectProperty, IndexedClassExpression>>();
		this.derived = new ArrayHashSet<IndexedClassExpression>(13);
		this.linksByObjectProperty = 
			new HashSetMultimap<IndexedObjectProperty, Context>(1);
		this.propagationsByObjectProperty = 
			new HashSetMultimap<IndexedObjectProperty, IndexedClassExpression> (1);
		this.isActive = new AtomicBoolean(false);
	}

	public IndexedClassExpression getRoot() {
		return root;
	}

	/**
	 * Sets the context as active if it was false. This method is thread safe:
	 * for two concurrent executions only one succeeds.
	 * 
	 * @return true if the context was not active; returns false otherwise
	 */
	protected boolean tryActivate() {
		return isActive.compareAndSet(false, true);
	}

	/**
	 * Sets the context as not active if it was active. This method is thread
	 * safe: for two concurrent executions only one succeeds.
	 * 
	 * @return true if the context was active; returns false otherwise
	 */
	protected boolean tryDeactivate() {
		return isActive.compareAndSet(true, false);
	}

	/**
	 * @return the set of derived indexed class expressions
	 */
	public Set<IndexedClassExpression> getDerived() {
		return derived;
	}

	private static int lastHashCode = 0;
	private final int hashCode_ = ++lastHashCode;

	/**
	 * Get an integer hash code to be used for this object. Care will be taken
	 * that there is never more than one Context per IndexedClassExpression
	 * root, so it is viable to use a running number as a hash here.
	 * 
	 * @return integer hash code
	 */
	@Override
	public final int hashCode() {
		return hashCode_;
	}
}