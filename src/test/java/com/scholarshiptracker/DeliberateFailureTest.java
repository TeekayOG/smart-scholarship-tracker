package com.scholarshiptracker;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Demonstrates a deliberately failing test case for Appendix A report evidence.
 * Disabled by default to ensure automated GitHub Actions CI builds remain green.
 */
class DeliberateFailureTest {

    @Test
    @Disabled("Deliberately failing test case documented for assignment report Appendix A; disabled to maintain green CI build")
    void evaluate_deliberateFailureDemo_throwsAssertionError() {
        fail("Deliberate failure demonstration for written technical report Appendix A.");
    }
}