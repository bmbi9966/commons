package net.dongliu.commons;

import net.dongliu.commons.exception.UndeclaredLambdaException;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Wrap checked interface to unchecked interface.
 * <p>
 * If exception occurred, unchecked exceptions will be thrown directly,
 * {@link IOException} will be wrapped in {@link UncheckedIOException},
 * other checked exceptions will be wrapped in {@link UndeclaredLambdaException}
 * </p>
 *
 * @deprecated use {@link UncheckLambdas}
 */
@Deprecated
public class Unchecked extends UncheckLambdas {

}
