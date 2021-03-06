/*
  The MIT License (MIT)

  Copyright (c) 2014-2017 Marc de Verdelhan & respective authors (see AUTHORS)

  Permission is hereby granted, free of charge, to any person obtaining a copy of
  this software and associated documentation files (the "Software"), to deal in
  the Software without restriction, including without limitation the rights to
  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
  the Software, and to permit persons to whom the Software is furnished to do so,
  subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core.indicators.statistics;

import org.ta4j.core.Decimal;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;

/**
 * Sigma-Indicator (also called, "z-score" or "standard score").
 * <p/>
 * see http://www.statisticshowto.com/probability-and-statistics/z-score/
 */
public class SigmaIndicator extends CachedIndicator<Decimal> {

    private static final long serialVersionUID = 6283425887025798038L;
    
    private Indicator<Decimal> ref;
    private int timeFrame;

    private SMAIndicator mean;
    private StandardDeviationIndicator sd;
    
    /**
     * Constructor.
     * @param ref the indicator
     * @param timeFrame the time frame
     */
    public SigmaIndicator(Indicator<Decimal> ref, int timeFrame) {
        super(ref);
        this.ref = ref;
        this.timeFrame = timeFrame;
        mean = new SMAIndicator(ref, timeFrame);
        sd = new StandardDeviationIndicator(ref, timeFrame);
    }

    @Override
    protected Decimal calculate(int index) {
        // z-score = (ref - mean) / sd
        return (ref.getValue(index).minus(mean.getValue(index))).dividedBy(sd.getValue(index));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " timeFrame: " + timeFrame;
    }
}
