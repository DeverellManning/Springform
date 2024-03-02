package com.telee.springform;

import com.badlogic.gdx.InputProcessor;

public class SpringformInputProccesor implements InputProcessor {
	public boolean keyDown (int keycode) {
		return false;
	}

	public boolean keyUp (int keycode) {
		return false;
	}

	public boolean keyTyped (char character) {
		return false;
	}

	public boolean touchDown (int x, int y, int pointer, int button) {
		Desktop.pointer.mouseDown(button);
		return false;
	}

	public boolean touchUp (int x, int y, int pointer, int button) {
		Desktop.pointer.mouseUp(button);
		return false;
	}

	public boolean touchDragged (int x, int y, int pointer) {
		return false;
	}

	public boolean mouseMoved (int x, int y) {
		return false;
	}

	public boolean scrolled (float amountX, float amountY) {
		Desktop.pointer.mouseScrolled(amountY);
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}
}
