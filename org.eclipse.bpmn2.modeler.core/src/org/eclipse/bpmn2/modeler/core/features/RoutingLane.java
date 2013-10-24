/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

/**
 * This class represents a "routing lane" or blank space between shapes, which can
 * be used to lay connection lines. Routing Lanes are linked together in a network;
 * the linkage is created when two RoutingLane nodes share a common left or right
 * edge. See also RoutingNet.
 */
public class RoutingLane {
	protected final static List<RoutingLane> EMPTY_LIST = new ArrayList<RoutingLane>();
	protected Rectangle rect;
	protected List<RoutingLane> leftAdjacent;
	protected List<RoutingLane> rightAdjacent;
	protected ContainerShape shape;

	public static enum Adjacence { LEFT, TOP, BOTTOM, RIGHT, NONE };
	
	public RoutingLane(int x, int y, int width, int height) {
		this(new Rectangle(x,y,width,height));
	}
	
	private RoutingLane(Rectangle r) {
		rect = new Rectangle(r);
	}
	
	public RoutingLane.Adjacence adjacent(RoutingLane node) {
		return adjacent(node.rect);
	}
	
	public RoutingLane.Adjacence adjacent(Rectangle other) {
		if (rect.y==other.bottom()) {
			if (rect.right()<=other.x || other.right()<=rect.x)
				return Adjacence.NONE;
			return Adjacence.TOP;
		}
		if (rect.x==other.right()) {
			if (rect.bottom()<=other.y || other.bottom()<=rect.y)
				return Adjacence.NONE;
			return Adjacence.LEFT;
		}
		if (rect.right()==other.x) {
			if (rect.bottom()<=other.y || other.bottom()<=rect.y)
				return Adjacence.NONE;
			return Adjacence.RIGHT;
		}
		if (rect.bottom()==other.y) {
			if (rect.right()<=other.x || other.right()<=rect.x)
				return Adjacence.NONE;
			return Adjacence.BOTTOM;
		}
		return Adjacence.NONE;
	}
	
	public boolean intersects(RoutingLane node) {
		return intersects(node.rect);
	}
	
	public boolean intersects(Rectangle rect) {
		return GraphicsUtil.intersects(rect.x+1, rect.y+1, rect.width-2, rect.height-2,
				this.rect.x+1, this.rect.y+1, this.rect.width-2, this.rect.height-2);
	}
	
	public void addLeft(RoutingLane node) {
		if (leftAdjacent==null)
			leftAdjacent = new ArrayList<RoutingLane>();
		if (!leftAdjacent.contains(node))
			leftAdjacent.add(node);
		if (node.rightAdjacent==null)
			node.rightAdjacent = new ArrayList<RoutingLane>();
		if (!node.rightAdjacent.contains(this))
			node.rightAdjacent.add(this);
	}
	
	public boolean hasLeft() {
		return leftAdjacent!=null && leftAdjacent.size()>0;
	}
	
	public List<RoutingLane> getLeft() {
		if (hasLeft())
			return leftAdjacent;
		return EMPTY_LIST;
	}
	
	public void addRight(RoutingLane node) {
		if (rightAdjacent==null)
			rightAdjacent = new ArrayList<RoutingLane>();
		if (!rightAdjacent.contains(node))
			rightAdjacent.add(node);
		if (node.leftAdjacent==null)
			node.leftAdjacent = new ArrayList<RoutingLane>();
		if (!node.leftAdjacent.contains(this))
			node.leftAdjacent.add(this);
	}
	
	public boolean hasRight() {
		return rightAdjacent!=null && rightAdjacent.size()>0;
	}
	
	public List<RoutingLane> getRight() {
		if (hasRight())
			return rightAdjacent;
		return EMPTY_LIST;
	}

	public int getX() {
		return rect.x;
	}

	public int getY() {
		return rect.y;
	}

	public void setX(int i) {
		rect.x = i;
	}
	
	public void setY(int i) {
		rect.y = i;
	}
	
	public int getWidth() {
		return rect.width;
	}

	public int getHeight() {
		return rect.height;
	}
	
	public void setWidth(int i) {
		rect.width = i;
	}

	public void setHeight(int i) {
		rect.height = i;
	}

	public void setShape(PictogramElement shape) {
		this.shape = (ContainerShape)shape;
	}
	
	public ContainerShape getShape() {
		return shape;
	}

	@Override
	public boolean equals(Object that) {
		if (that instanceof RoutingLane)
			return this.rect.equals(((RoutingLane)that).rect);
		return false;
	}

	public void rotate(boolean b) {
		RoutingNet.rotateRectangle(rect);
	}

	public void navigateTo(RoutingLane ta, RoutingNet owner) {
		owner.push(this);
		if (this==ta) {
			owner.solutionFound();
		}
		else {
			for (RoutingLane a : getRight()) {
				if (!owner.visited(a))
					a.navigateTo(ta, owner);
			}
			for (RoutingLane a : getLeft()) {
				if (!owner.visited(a))
					a.navigateTo(ta, owner);
			}
		}
		owner.pop();
	}
}
