package dubrovnik.card.hardcode.dubrovnikcard;

import com.estimote.sdk.Region;

/**
 * Created by Ivan on 09/05/15.
 */
public interface Constants {
    static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    static final Region DUBROVNIK_LANDMARKS = new Region("regionId", ESTIMOTE_PROXIMITY_UUID, 53283, null);
    static final Region KNEZ_DVOR_LANDMARKS = new Region("kzLand", ESTIMOTE_PROXIMITY_UUID, 32379, null);
    static final int SOME_ART_MINOR = 64711;
}
