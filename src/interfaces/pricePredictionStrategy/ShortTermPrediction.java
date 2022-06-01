package interfaces.pricePredictionStrategy;

import model.CurrencyModel;
import utilities.CandleStick;
import utilities.PriceDirection;

import java.io.Serial;
import java.io.Serializable;

public class ShortTermPrediction implements PricePredictor, Serializable {
    @Serial
    private static final long serialVersionUID = -8044863200610707136L;

    @Override
    public SugestedAction getSuggestedAction(CurrencyModel currencyModel) {
        CandleStick lastCandleStick = null;
        CandleStick oneBeforeLastCandleStick = null;
        try {
            lastCandleStick = currencyModel.getPacketToDraw(2).getCandleSticks().get(1);
            oneBeforeLastCandleStick = currencyModel.getPacketToDraw(2).getCandleSticks().get(0);
        } catch (Exception e) {
            return SugestedAction.NOT_DETERMINED;
        }

        if(lastCandleStick.getColor() == PriceDirection.DOWN &&
                oneBeforeLastCandleStick.getColor() == PriceDirection.DOWN){
            return SugestedAction.SELL;
        }
        if(lastCandleStick.getColor() == PriceDirection.UP &&
                oneBeforeLastCandleStick.getColor() == PriceDirection.UP){
            return SugestedAction.BUY;
        }

        return SugestedAction.NOT_DETERMINED;
    }
}
