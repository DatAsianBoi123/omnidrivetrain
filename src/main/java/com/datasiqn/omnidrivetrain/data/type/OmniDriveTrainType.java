package com.datasiqn.omnidrivetrain.data.type;

import com.datasiqn.omnidrivetrain.data.OmniDriveTrainData;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class OmniDriveTrainType extends ComplexDataType<OmniDriveTrainData> {
    public static final String NAME = "OMNI_DRIVETRAIN";
    public static final OmniDriveTrainType INSTANCE = new OmniDriveTrainType();

    private OmniDriveTrainType() {
        super(NAME, OmniDriveTrainData.class);
    }

    @Override
    public Function<Map<String, Object>, OmniDriveTrainData> fromMap() {
        return map -> {
            double heading = (double) map.getOrDefault("heading", 0);
            double leftPower = (double) map.getOrDefault("leftPower", 0);
            double topPower = (double) map.getOrDefault("topPower", 0);
            double rightPower = (double) map.getOrDefault("rightPower", 0);
            double bottomPower = (double) map.getOrDefault("bottomPower", 0);
            return new OmniDriveTrainData(heading, leftPower, topPower, rightPower, bottomPower);
        };
    }

    @Override
    public OmniDriveTrainData getDefaultValue() {
        return new OmniDriveTrainData(0, 0, 0, 0, 0);
    }
}
