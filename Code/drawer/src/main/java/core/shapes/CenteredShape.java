package core.shapes;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public abstract class CenteredShape extends Shape {

    protected int xCentre;
    protected int yCentre;
    
}
