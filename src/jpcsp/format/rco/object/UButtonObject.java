/*
This file is part of jpcsp.

Jpcsp is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jpcsp is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Jpcsp.  If not, see <http://www.gnu.org/licenses/>.
 */
package jpcsp.format.rco.object;

import java.awt.image.BufferedImage;

import jpcsp.format.rco.ObjectField;
import jpcsp.format.rco.type.EventType;
import jpcsp.format.rco.type.ImageType;
import jpcsp.format.rco.type.IntType;

public class UButtonObject extends BasePositionObject {
	@ObjectField(order = 201)
	public ImageType image;
	@ObjectField(order = 202)
	public EventType onPush;
	@ObjectField(order = 203)
	public EventType onFocusIn;
	@ObjectField(order = 204)
	public EventType onFocusOut;
	@ObjectField(order = 205)
	public EventType onLeft;
	@ObjectField(order = 206)
	public EventType onRight;
	@ObjectField(order = 207)
	public EventType onUp;
	@ObjectField(order = 208)
	public EventType onDown;
	@ObjectField(order = 209)
	public IntType unknownInt32;

	@Override
	public BufferedImage getImage() {
		return image.getImage();
	}

	@Override
	public void onUp() {
		controller.getInterpreter().interpretScript(getObject(), onUp.getEvent());
	}

	@Override
	public void onDown() {
		controller.getInterpreter().interpretScript(getObject(), onDown.getEvent());
	}

	@Override
	public void onLeft() {
		controller.getInterpreter().interpretScript(getObject(), onLeft.getEvent());
	}

	@Override
	public void onRight() {
		controller.getInterpreter().interpretScript(getObject(), onRight.getEvent());
	}

	@Override
	public void onPush() {
		controller.getInterpreter().interpretScript(getObject(), onPush.getEvent());
	}
}