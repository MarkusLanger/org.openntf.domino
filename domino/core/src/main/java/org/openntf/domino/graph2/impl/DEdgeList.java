package org.openntf.domino.graph2.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class DEdgeList implements org.openntf.domino.graph2.DEdgeList, Iterable<Edge> {
	private static final long serialVersionUID = 1L;
	protected final DVertex sourceVertex_;
	protected List<Edge> delegate_;

	private DEdgeList(final DVertex source, final List<Edge> list) {
		sourceVertex_ = source;
		delegate_ = list;
	}

	public DEdgeList(final DVertex source) {
		sourceVertex_ = source;
		delegate_ = new ArrayList<Edge>();
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.graph2.impl.IEdgeList#atomic()
	 */
	@Override
	public DEdgeList atomic() {
		return this;
		//		return new DEdgeList(sourceVertex_, new AtomicTableImpl<Edge>(service()));
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.graph2.impl.IEdgeList#unmodifiable()
	 */
	@Override
	public DEdgeList unmodifiable() {
		return this;
		//		return new DEdgeList(sourceVertex_, new UnmodifiableTableImpl<Edge>(service()));
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.graph2.impl.IEdgeList#findEdge(com.tinkerpop.blueprints.Vertex)
	 */
	@Override
	public Edge findEdge(final Vertex toVertex) {
		Edge result = null;
		Object toId = toVertex.getId();
		Object fromId = sourceVertex_.getId();
		if (this.size() > 0) {
			for (Edge edge : this) {
				if (edge instanceof DEdge) {
					DEdge dedge = (DEdge) edge;
					if (toId.equals(dedge.getOtherVertexId(sourceVertex_))) {
						result = dedge;
						break;
					}
				} else {
					Vertex inVertex = edge.getVertex(Direction.IN);
					if (fromId.equals(inVertex.getId())) {
						if (toId.equals(edge.getVertex(Direction.OUT))) {
							result = edge;
							break;
						}
					} else if (toId.equals(inVertex.getId())) {
						result = edge;
						break;
					}
				}
			}
		} else {
			//			System.out.println("DEBUG: No edges defined in EdgeList");
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.graph2.impl.IEdgeList#applyFilter(java.lang.String, java.lang.Object)
	 */
	@Override
	public DEdgeList applyFilter(final String key, final Object value) {
		DEdgeList result = new DEdgeList(sourceVertex_);
		if (this.size() > 0) {
			for (Edge edge : this) {
				if (edge instanceof DEdge) {
					DEdge dedge = (DEdge) edge;
					if (value.equals(dedge.getProperty(key))) {
						result.add(edge);
					}
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.graph2.impl.IEdgeList#toVertexList()
	 */
	@Override
	public DVertexList toVertexList() {
		DVertexList result = new DVertexList(sourceVertex_);
		if (this.size() > 0) {
			for (Edge edge : this) {
				if (edge instanceof DEdge) {
					DEdge dedge = (DEdge) edge;
					DVertex vert = (DVertex) dedge.getOtherVertex(sourceVertex_);
					result.add(vert);
				}
			}
		}
		return result;
	}

	@Override
	public boolean add(final Edge e) {
		return delegate_.add(e);
	}

	@Override
	public void add(final int index, final Edge element) {
		delegate_.add(index, element);
	}

	@Override
	public boolean addAll(final Collection<? extends Edge> c) {
		return delegate_.addAll(c);
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends Edge> c) {
		return delegate_.addAll(index, c);
	}

	@Override
	public void clear() {
		delegate_.clear();
	}

	@Override
	public boolean contains(final Object o) {
		return delegate_.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return delegate_.containsAll(c);
	}

	@Override
	public Edge get(final int index) {
		return delegate_.get(index);
	}

	@Override
	public int indexOf(final Object o) {
		return delegate_.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return delegate_.isEmpty();
	}

	@Override
	public Iterator<Edge> iterator() {
		return delegate_.iterator();
	}

	@Override
	public int lastIndexOf(final Object o) {
		return delegate_.lastIndexOf(o);
	}

	@Override
	public ListIterator<Edge> listIterator() {
		return delegate_.listIterator();
	}

	@Override
	public ListIterator<Edge> listIterator(final int index) {
		return delegate_.listIterator(index);
	}

	@Override
	public Edge remove(final int index) {
		return delegate_.remove(index);
	}

	@Override
	public boolean remove(final Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return delegate_.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return delegate_.retainAll(c);
	}

	@Override
	public Edge set(final int index, final Edge element) {
		return delegate_.set(index, element);
	}

	@Override
	public int size() {
		return delegate_.size();
	}

	@Override
	public List<Edge> subList(final int fromIndex, final int toIndex) {
		return new DEdgeList(sourceVertex_, delegate_.subList(fromIndex, toIndex));
	}

	@Override
	public Object[] toArray() {
		return delegate_.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return delegate_.toArray(a);
	}

}
