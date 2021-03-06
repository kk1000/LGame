package loon.component.skin;

import loon.LSystem;
import loon.LTexture;
import loon.canvas.LColor;
import loon.component.DefUI;
import loon.font.FontSet;
import loon.font.IFont;

public class TextListSkin implements FontSet<TextListSkin>{

	private LTexture backgoundTexture;
	private LTexture choiceTexture;
	private LTexture scrollTexture;
	private LTexture scrollFlagATexture;
	private LTexture scrollFlagBTexture;
	private IFont font;
	private LColor fontColor;

	public final static TextListSkin def() {
		return new TextListSkin();
	}

	public TextListSkin() {
		this(LSystem.getSystemGameFont(), LColor.white, DefUI.get().getDefaultTextures(2),
				DefUI.get().getDefaultTextures(4), DefUI.get().getDefaultTextures(3),
				null, null);
	}

	public TextListSkin(IFont font, LColor fontColor, LTexture bg,
			LTexture choice, LTexture scroll, LTexture scrollFlagA,
			LTexture scrollFlagB) {
		this.font = font;
		this.fontColor = fontColor;
		this.backgoundTexture = bg;
		this.choiceTexture = choice;
		this.scrollTexture = scroll;
		this.scrollFlagATexture = scrollFlagA;
		this.scrollFlagBTexture = scrollFlagB;
	}

	public LTexture getBackgoundTexture() {
		return backgoundTexture;
	}

	public void setBackgoundTexture(LTexture bgTexture) {
		this.backgoundTexture = bgTexture;
	}

	public LTexture getChoiceTexture() {
		return choiceTexture;
	}

	public void setChoiceTexture(LTexture choiceTexture) {
		this.choiceTexture = choiceTexture;
	}

	public LTexture getScrollTexture() {
		return scrollTexture;
	}

	public void setScrollTexture(LTexture scrollTexture) {
		this.scrollTexture = scrollTexture;
	}

	public LTexture getScrollFlagATexture() {
		return scrollFlagATexture;
	}

	public void setScrollFlagATexture(LTexture scrollFlagATexture) {
		this.scrollFlagATexture = scrollFlagATexture;
	}

	public LTexture getScrollFlagBTexture() {
		return scrollFlagBTexture;
	}

	public void setScrollFlagBTexture(LTexture scrollFlagBTexture) {
		this.scrollFlagBTexture = scrollFlagBTexture;
	}

	public IFont getFont() {
		return font;
	}

	public TextListSkin setFont(IFont font) {
		this.font = font;
		return this;
	}

	public LColor getFontColor() {
		return fontColor;
	}

	public void setFontColor(LColor fontColor) {
		this.fontColor = fontColor;
	}

}
