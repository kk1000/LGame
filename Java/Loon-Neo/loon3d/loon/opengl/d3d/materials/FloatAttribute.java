package loon.opengl.d3d.materials;

public class FloatAttribute extends Material.Attribute {
	
    public static final String ShininessAlias = "shininess";
    public static final long Shininess = register(ShininessAlias);

    public static FloatAttribute createShininess(float value) {
        return new FloatAttribute(Shininess, value);
    }

    public static final String AlphaTestAlias = "alphaTest";
    public static final long AlphaTest = register(AlphaTestAlias);

    public static FloatAttribute createAlphaTest(float value) {
        return new FloatAttribute(AlphaTest, value);
    }

    public float value;

    public FloatAttribute(long type) {
        super(type);
    }

    public FloatAttribute(long type, float value) {
        super(type);
        this.value = value;
    }

    @Override
    public Material.Attribute cpy () {
        return new FloatAttribute(type, value);
    }

    @Override
    protected boolean equals (Material.Attribute other) {
        return ((FloatAttribute)other).value == value;
    }
}
