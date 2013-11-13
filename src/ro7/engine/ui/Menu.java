package ro7.engine.ui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs195n.Vec2f;

public class Menu {

	private float spacing;
	private Vec2f position;
	private int selectedIndex;
	private List<MenuElement> elements;
	
	public Menu(Vec2f position, float spacing)
	{
		this.position = position;
		this.spacing = spacing;
		selectedIndex = 0;
		elements = new ArrayList<MenuElement>();
	}
	
	public void addElement(MenuElement element)
	{
		element.moveTo(getNewElementPosition());
		elements.add(element);
	}
	
	public Vec2f getNewElementPosition()
	{
		int index = elements.size();
		return new Vec2f(position.x, position.y + (spacing * index));
	}
	
	public void moveIndex(int amount)
	{
		int newIndex = amount+selectedIndex;
		if(newIndex >= elements.size())
			newIndex = elements.size() - 1;
		else if (newIndex < 0)
			newIndex = 0;
		selectedIndex = newIndex;
	}
	
	public void update(long nanoseconds) {
		if(!elements.isEmpty())
		{
			for(int i = 0; i < elements.size(); i++)
			{
				MenuElement element = elements.get(i);
				if(i == selectedIndex)
					element.isSelected = true;
				else
					element.isSelected = false;
				element.update(nanoseconds);
			}
		}
	}
	
	public void draw(Graphics2D g)
	{
		for (MenuElement element : elements)
			element.draw(g);
	}
	
}
