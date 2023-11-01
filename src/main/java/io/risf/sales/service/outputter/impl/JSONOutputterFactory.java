package io.risf.sales.service.outputter.impl;

import io.risf.sales.service.outputter.Outputter;
import io.risf.sales.service.outputter.OutputterFactory;

public class JSONOutputterFactory implements OutputterFactory {
    @Override
    public Outputter createOutputter() {
        return new JSONOutputter();
    }
}