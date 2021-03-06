/*
 * Copyright (c) 2016 Jacob Rachiele
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Contributors:
 *
 * Jacob Rachiele
 */
package com.github.signaflo.math;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * A representation of a complex number. This class is immutable and thread-safe.
 *
 * @author Jacob Rachiele
 */
@EqualsAndHashCode
public final class Complex implements FieldElement<Complex> {

    private static final double EPSILON = Math.ulp(1.0);

    private final double real;
    private final double im;

    /**
     * Construct a new complex number with real and imaginary parts both equal to 0.
     */
    public Complex() {
        this(0.0, 0.0);
    }

    /**
     * Construct a new complex number with zero imaginary part, i.e, a real number.
     *
     * @param real the real part of the new complex number.
     */
    public Complex(final double real) {
        this(real, 0.0);
    }

    /**
     * Construct a new complex number with the given real and imaginary parts.
     *
     * @param real the real part of the new complex number.
     * @param im   the imaginary part of the new complex number.
     */
    public Complex(final double real, final double im) {
        this.real = real;
        this.im = im;
    }

    public static Complex from(Real real) {
        return new Complex(real.asDouble());
    }

    public static Complex zero() {
        return new Complex(0.0, 0.0);
    }

    @Override
    public final Complex plus(final Complex other) {
        return new Complex(this.real + other.real, this.im + other.im);
    }

    /**
     * Add this element to the given double.
     *
     * @param other the double to add to this element.
     * @return this element added to the given double.
     */
    public final Complex plus(final double other) {
        return new Complex(this.real + other, this.im);
    }

    @Override
    public final Complex minus(final Complex other) {
        return new Complex(this.real - other.real, this.im - other.im);
    }

    @Override
    public final Complex times(final Complex other) {
        final double realPart = this.real * other.real - this.im * other.im;
        final double imPart = this.real * other.im + other.real * this.im;
        return new Complex(realPart, imPart);
    }

    /**
     * Multiply this element by the given double.
     *
     * @param other the double to multiply this element by.
     * @return this element multiplied by the given double.
     */
    private Complex times(final double other) {
        return new Complex(this.real * other, this.im * other);
    }

    /**
     * Divide this element by the given double.
     *
     * @param value the double to divide this element by.
     * @return this element divided by the given double.
     */
    public final Complex dividedBy(final double value) {
        if (value == 0) {
            throw new IllegalArgumentException("Attempt to divide a complex number by zero.");
        }
        return new Complex(this.real / value, this.im / value);
    }

    @Override
    public final Complex dividedBy(final Complex value) {
        Complex top = new Complex(this.real * value.real + this.im * value.im,
                                  this.real * -value.im + value.real * this.im);
        double bottom = value.real * value.real + value.im * value.im;
        return top.dividedBy(bottom);
    }

    @Override
    public final Complex conjugate() {
        return new Complex(this.real, -this.im);
    }

    @Override
    public final double abs() {
        return Math.sqrt(real * real + im * im);
    }

    @Override
    public Complex additiveInverse() {
        return new Complex(-this.real, -this.im);
    }

    @Override
    public Complex complexSqrt() {
        if (this.real < EPSILON && Math.abs(this.im) < EPSILON) {
            return new Complex(0.0, Math.sqrt(abs()));
        }
        // The following algorithm fails only in the case where this complex number is
        // a negative real number, but that case was taken care of in the preceding if branch.
        // http://math.stackexchange.com/questions/44406/how-do-i-get-the-square-root-of-a-complex-number
        final double r = abs();
        final Complex zr = this.plus(r);
        return zr.dividedBy(zr.abs()).times(Math.sqrt(r));
    }

    @Override
    public Complex sqrt() {
        return complexSqrt();
    }

    /**
     * The real part of this complex number.
     *
     * @return the real part of this complex number.
     */
    public final double real() {
        return this.real;
    }

    /**
     * The imaginary part of this complex number.
     *
     * @return the imaginary part of this complex number.
     */
    public final double im() {
        return this.im;
    }

    /**
     * Returns true if this complex number is also a real number and false otherwise.
     *
     * @return true if this complex number is also a real number and false otherwise.
     */
    public final boolean isReal() {
        return Math.abs(this.im) < EPSILON;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Complex: ");
        if (Math.abs(this.real) > 0.0) {
            sb.append(Double.toString(this.real));
        } else {
            if (Math.abs(this.im) > 0.0) {
                return sb.append(im).append("i").toString();
            }
            return sb.append("0.0").toString();
        }

        if (im < 0.0) {
            sb.append(" - ").append(Math.abs(im)).append("i");
        } else if (im > 0.0) {
            sb.append(" + ").append(im).append("i");
        }
        return sb.toString();
    }

    @Override
    public int compareTo(@NonNull Complex other) {
        return Double.compare(this.abs(), other.abs());
    }
}
