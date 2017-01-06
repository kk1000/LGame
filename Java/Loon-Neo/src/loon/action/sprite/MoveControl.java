package loon.action.sprite;

import loon.LRelease;
import loon.action.ActionBind;
import loon.action.map.Config;
import loon.action.map.Field2D;
import loon.action.map.TileMap;
import loon.utils.processes.RealtimeProcess;
import loon.utils.processes.RealtimeProcessManager;
import loon.utils.timer.LTimerContext;

/**
 * 一个四方向(八方向)运动的控制器,主要用来键盘或虚拟摇杆控制角色移动
 */
public class MoveControl implements LRelease {

	private int _speed = 8;

	private int _px = 0, _py = 0, _direction = -1;

	private int _moveX = 0, _moveY = 0, _movingLength = 0;

	private boolean _isMoving = false, _running = false;

	private ActionBind _bind;

	private Field2D _map;

	public MoveControl(ActionBind bind, TileMap map) {
		this(bind, map.getField());
	}

	public MoveControl(ActionBind bind, Field2D field2d) {
		this._bind = bind;
		this._map = field2d;
	}

	public void setDirection(int d) {
		this._direction = d;
	}

	public void resetDirection() {
		setDirection(-1);
	}

	public int getDirection() {
		return this._direction;
	}

	public final void call() {
		move(_bind, _map, _direction);
	}

	public MoveControl start() {
		if (!_running) {
			RealtimeProcess process = new RealtimeProcess() {

				@Override
				public void run(LTimerContext time) {
					if (_running) {
						call();
					} else {
						kill();
					}
				}
			};
			process.setDelay(30);
			_running = true;
			RealtimeProcessManager.get().addProcess(process);
		}
		return this;
	}

	public MoveControl stop() {
		this._running = false;
		return this;
	}

	public final boolean move(ActionBind bind, Field2D field2d, int direction) {
		boolean notMove = false;
		this._movingLength = 0;
		this._px = bind.x();
		this._py = bind.y();
		this._moveX = field2d.pixelsToTilesWidth(_px);
		this._moveY = field2d.pixelsToTilesHeight(_py);
		switch (direction) {
		case Config.TLEFT:
			if (moveTLeft(field2d)) {
				notMove = true;
			}
			break;
		case Config.LEFT:
			if (moveLeft(field2d)) {
				notMove = true;
			}
			break;
		case Config.TRIGHT:
			if (moveTRight(field2d)) {
				notMove = true;
			}
			break;
		case Config.RIGHT:
			if (moveRight(field2d)) {
				notMove = true;
			}
			break;
		case Config.TUP:
			if (moveTUp(field2d)) {
				notMove = true;
			}
			break;
		case Config.UP:
			if (moveUp(field2d)) {
				notMove = true;
			}
			break;
		case Config.TDOWN:
			if (moveTDown(field2d)) {
				notMove = true;
			}
			break;
		case Config.DOWN:
			if (moveDown(field2d)) {
				notMove = true;
			}
			break;
		}
		if (!notMove) {
			bind.setX(_px);
			bind.setY(_py);
		}
		return notMove;
	}

	private boolean moveLeft(Field2D field2d) {
		int nextX = _moveX - 1;
		int nextY = _moveY - 1;
		if (nextX < 0) {
			nextX = 0;
		}
		if (nextY < 0) {
			nextY = 0;
		}
		if (field2d.isHit(nextX, nextY)) {
			_px -= _speed;
			if (_px < 0) {
				_px = 0;
			}
			_py -= _speed;
			if (_py < 0) {
				_py = 0;
			}
			_movingLength += _speed;
			if (_movingLength >= field2d.getTileWidth()) {
				_moveX--;
				_px = _moveX * field2d.getTileWidth();
				_isMoving = false;
				return true;
			}
			if (_movingLength >= field2d.getTileHeight()) {
				_moveY--;
				_py = _moveY * field2d.getTileHeight();
				_isMoving = false;
				return true;
			}
		} else {
			_isMoving = false;
			_px = _moveX * field2d.getTileWidth();
			_py = _moveY * field2d.getTileHeight();
		}
		return false;
	}

	private boolean moveTLeft(Field2D field2d) {
		int nextX = _moveX - 1;
		int nextY = _moveY;
		if (nextX < 0) {
			nextX = 0;
		}
		if (field2d.isHit(nextX, nextY)) {
			_px -= _speed;
			if (_px < 0) {
				_px = 0;
			}
			_movingLength += _speed;
			if (_movingLength >= field2d.getTileWidth()) {
				_moveX--;
				_px = _moveX * field2d.getTileWidth();
				_isMoving = false;
				return true;
			}
		} else {
			_isMoving = false;
			_px = _moveX * field2d.getTileWidth();
			_py = _moveY * field2d.getTileHeight();
		}
		return false;
	}

	private boolean moveRight(Field2D field2d) {
		int nextX = _moveX + 1;
		int nextY = _moveY + 1;
		if (nextX > field2d.getWidth() - 1) {
			nextX = field2d.getWidth() - 1;
		}
		if (nextY > field2d.getHeight() - 1) {
			nextY = field2d.getHeight() - 1;
		}
		if (field2d.isHit(nextX, nextY)) {
			_px += _speed;
			if (_px > field2d.getViewWidth() - field2d.getTileWidth()) {
				_px = field2d.getViewWidth() - field2d.getTileWidth();
			}
			_py += _speed;
			if (_py > field2d.getViewHeight() - field2d.getTileHeight()) {
				_py = field2d.getViewHeight() - field2d.getTileHeight();
			}
			_movingLength += _speed;
			if (_movingLength >= field2d.getTileWidth()) {
				_moveX++;
				_px = _moveX * field2d.getTileWidth();
				_isMoving = false;
				return true;
			}
			if (_movingLength >= field2d.getTileHeight()) {
				_moveY++;
				_py = _moveY * field2d.getTileHeight();
				_isMoving = false;
				return true;
			}
		} else {
			_isMoving = false;
			_px = _moveX * field2d.getTileWidth();
			_py = _moveY * field2d.getTileHeight();
		}

		return false;
	}

	private boolean moveTRight(Field2D field2d) {
		int nextX = _moveX + 1;
		int nextY = _moveY;
		if (nextX > field2d.getWidth() - 1) {
			nextX = field2d.getWidth() - 1;
		}
		if (field2d.isHit(nextX, nextY)) {
			_px += _speed;
			if (_px > field2d.getViewWidth() - field2d.getTileWidth()) {
				_px = field2d.getViewWidth() - field2d.getTileWidth();
			}
			_movingLength += _speed;
			if (_movingLength >= field2d.getTileWidth()) {
				_moveX++;
				_px = _moveX * field2d.getTileWidth();
				_isMoving = false;
				return true;
			}
		} else {
			_isMoving = false;
			_px = _moveX * field2d.getTileWidth();
			_py = _moveY * field2d.getTileHeight();
		}

		return false;
	}

	private boolean moveUp(Field2D field2d) {
		int nextX = _moveX + 1;
		int nextY = _moveY - 1;
		if (nextX > field2d.getWidth() - 1) {
			nextX = field2d.getWidth() - 1;
		}
		if (nextY < 0) {
			nextY = 0;
		}
		if (field2d.isHit(nextX, nextY)) {
			_px += _speed;
			if (_px > field2d.getViewWidth() - field2d.getTileWidth()) {
				_px = field2d.getViewWidth() - field2d.getTileWidth();
			}
			_movingLength += _speed;
			if (_movingLength >= field2d.getTileWidth()) {
				_moveX++;
				_px = _moveX * field2d.getTileWidth();
				_isMoving = false;
				return true;
			}
			_py -= _speed;
			if (_py < 0) {
				_py = 0;
			}
			if (_movingLength >= field2d.getTileHeight()) {
				_moveY--;
				_py = _moveY * field2d.getTileHeight();
				_isMoving = false;
				return true;
			}
		} else {
			_isMoving = false;
			_px = _moveX * field2d.getTileWidth();
			_py = _moveY * field2d.getTileHeight();
		}

		return false;
	}

	private boolean moveTUp(Field2D field2d) {
		int nextX = _moveX;
		int nextY = _moveY - 1;
		if (nextY < 0) {
			nextY = 0;
		}
		if (field2d.isHit(nextX, nextY)) {
			_py -= _speed;
			if (_py < 0) {
				_py = 0;
			}
			_movingLength += _speed;
			if (_movingLength >= field2d.getTileHeight()) {
				_moveY--;
				_py = _moveY * field2d.getTileHeight();
				_isMoving = false;
				return true;
			}
		} else {
			_isMoving = false;
			_px = _moveX * field2d.getTileWidth();
			_py = _moveY * field2d.getTileHeight();
		}

		return false;
	}

	private boolean moveDown(Field2D field2d) {
		int nextX = _moveX - 1;
		int nextY = _moveY + 1;
		if (nextX < 0) {
			nextX = 0;
		}
		if (nextY > field2d.getHeight() - 1) {
			nextY = field2d.getHeight() - 1;
		}
		if (field2d.isHit(nextX, nextY)) {
			_px -= _speed;
			if (_px < 0) {
				_px = 0;
			}
			_movingLength += _speed;
			if (_movingLength >= field2d.getTileWidth()) {
				_moveX--;
				_px = _moveX * field2d.getTileWidth();
				_isMoving = false;
				return true;
			}
			_py += _speed;
			if (_py > field2d.getViewHeight() - field2d.getTileHeight()) {
				_py = field2d.getViewHeight() - field2d.getTileHeight();
			}
			if (_movingLength >= field2d.getTileHeight()) {
				_moveY++;
				_py = _moveY * field2d.getTileHeight();
				_isMoving = false;
				return true;
			}
		} else {
			_isMoving = false;
			_px = _moveX * field2d.getTileWidth();
			_py = _moveY * field2d.getTileHeight();
		}
		return false;
	}

	private boolean moveTDown(Field2D field2d) {
		int nextX = _moveX;
		int nextY = _moveY + 1;
		if (nextY > field2d.getHeight() - 1) {
			nextY = field2d.getHeight() - 1;
		}
		if (field2d.isHit(nextX, nextY)) {
			_py += _speed;
			if (_py > field2d.getViewHeight() - field2d.getTileHeight()) {
				_py = field2d.getViewHeight() - field2d.getTileHeight();
			}
			_movingLength += _speed;
			if (_movingLength >= field2d.getTileHeight()) {
				_moveY++;
				_py = _moveY * field2d.getTileHeight();
				_isMoving = false;
				return true;
			}
		} else {
			_isMoving = false;
			_px = _moveX * field2d.getTileWidth();
			_py = _moveY * field2d.getTileHeight();
		}
		return false;
	}

	public boolean isMoving() {
		return _isMoving;
	}

	public int getSpeed() {
		return _speed;
	}

	public void setSpeed(int s) {
		this._speed = s;
	}

	public boolean isRunning() {
		return _running;
	}

	@Override
	public void close() {
		_running = false;
	}

}
