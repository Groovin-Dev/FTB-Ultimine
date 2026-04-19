package dev.ftb.mods.ftbultimine.shape;

import dev.ftb.mods.ftbultimine.FTBUltimine;
import dev.ftb.mods.ftbultimine.api.shape.RegisterShapeEvent;
import dev.ftb.mods.ftbultimine.api.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class ShapeRegistry {
    private static final ShapeRegistry CLIENT_INSTANCE = new ShapeRegistry();
    private static final ShapeRegistry SERVER_INSTANCE = new ShapeRegistry();

    // list of all known shapes
    private final List<Shape> shapesList = new CopyOnWriteArrayList<>();

    public static ShapeRegistry getInstance(boolean clientSide) {
        return clientSide ? CLIENT_INSTANCE : SERVER_INSTANCE;
    }

    @Nullable
    private Shape defaultShape = null;

    /// Register a new shape. Only call this via [RegisterShapeEvent.Data#register] !
    ///
    /// @param shape the shape to register
    public void register(Shape shape) {
        shapesList.add(shape);

        if (shape.isDefault()) {
            if (defaultShape != null) {
                FTBUltimine.LOGGER.warn("default shape already set to {}! ignoring attempt to make {} default",
                        defaultShape.getName(), shape.getName());
            } else {
                defaultShape = shape;
            }
        }
    }

    public Shape getShape(int idx) {
        if (idx < 0) {
            idx += shapesList.size();
        } else if (idx >= shapesList.size()) {
            idx -= shapesList.size();
        }
        return idx >= 0 && idx < shapesList.size() ? shapesList.get(idx) : Objects.requireNonNull(defaultShape);
    }

    public int shapeCount() {
        return shapesList.size();
    }

    @Nullable
    public Shape getDefaultShape() {
        return defaultShape;
    }
}
