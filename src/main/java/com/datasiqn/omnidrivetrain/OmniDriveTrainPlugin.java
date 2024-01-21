package com.datasiqn.omnidrivetrain;

import com.datasiqn.omnidrivetrain.data.type.OmniDriveTrainType;
import com.datasiqn.omnidrivetrain.widget.OmniDriveTrainWidget;
import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import java.util.List;
import java.util.Map;

@Description(group = "com.datasiqn", name = "Omni Drivetrain Plugin", version = "1.0.0", summary = "Plugin to use with an Omni Drivetrain")
public class OmniDriveTrainPlugin extends Plugin {
    @Override
    public List<DataType> getDataTypes() {
        return List.of(OmniDriveTrainType.INSTANCE);
    }

    @Override
    public List<ComponentType> getComponents() {
        return List.of(WidgetType.forAnnotatedWidget(OmniDriveTrainWidget.class));
    }

    @Override
    public Map<DataType, ComponentType> getDefaultComponents() {
        return Map.of(OmniDriveTrainType.INSTANCE, WidgetType.forAnnotatedWidget(OmniDriveTrainWidget.class));
    }
}
