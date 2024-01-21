package com.datasiqn.omnidrivetrain.data;

import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

public class OmniDriveTrainData extends ComplexData<OmniDriveTrainData> {
    private final double heading;
    private final double leftPower;
    private final double topPower;
    private final double rightPower;
    private final double bottomPower;

    public OmniDriveTrainData(double heading, double leftPower, double topPower, double rightPower, double bottomPower) {
        this.heading = heading;
        this.leftPower = leftPower;
        this.topPower = topPower;
        this.rightPower = rightPower;
        this.bottomPower = bottomPower;
    }

    public double getHeading() {
        return heading;
    }

    public double getLeftPower() {
        return leftPower;
    }

    public double getTopPower() {
        return topPower;
    }

    public double getRightPower() {
        return rightPower;
    }

    public double getBottomPower() {
        return bottomPower;
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("heading", heading);
        map.put("leftPower", leftPower);
        map.put("topPower", topPower);
        map.put("rightPower", rightPower);
        map.put("bottomPower", bottomPower);
        return map;
    }
}
