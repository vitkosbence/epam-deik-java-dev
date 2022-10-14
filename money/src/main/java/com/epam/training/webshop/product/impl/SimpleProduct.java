package com.epam.training.webshop.product.impl;

import com.epam.training.webshop.product.Product;

import java.util.Objects;

public final class SimpleProduct implements Product {

    private final String name;
    private final double netPrice;
    private final String packaging;

    private SimpleProduct(SimpleProductBuilder builder) {
        this.name = builder.name;
        this.netPrice = builder.netPrice;
        this.packaging = builder.packaging;
    }

    public static SimpleProductBuilder builder(String name) {
        return new SimpleProductBuilder(name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getNetPrice() {
        return this.netPrice;
    }

    @Override
    public String getPackaging() {
        return this.packaging;
    }


    @Override
    public String toString() {
        return "SimpleProduct{" +
                "name='" + name + '\'' +
                ", netPrice=" + netPrice +
                ", packaging='" + packaging + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SimpleProduct that = (SimpleProduct) o;
        return Double.compare(that.netPrice, netPrice) == 0 && Objects.equals(name, that.name) && Objects.equals(packaging, that.packaging);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, netPrice, packaging);
    }

    public static class SimpleProductBuilder {
        private final String name;
        private double netPrice;
        private String packaging;

        public SimpleProductBuilder(String name) {
            this.name = name;
        }

//        public SimpleProductBuilder withName(String name) {
//            this.name = name;
//            return this;
//        }

        public SimpleProductBuilder withNetPrice(double netPrice) {
            this.netPrice = netPrice;
            return this;
        }

        public SimpleProductBuilder withPackaging(String packaging) {
            this.packaging = packaging;
            return this;
        }

        public SimpleProduct build() {
            return new SimpleProduct(this);
        }
    }
}
