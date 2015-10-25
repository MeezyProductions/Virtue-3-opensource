/*
 * FilterDecoder
 *
 * Author: Lasse Collin <lasse.collin@tukaani.org>
 *
 * This file has been put into the public domain.
 * You can do whatever you want with this file.
 */

package org.virtue.openrs.utility.tukaani;

import java.io.InputStream;

interface FilterDecoder extends FilterCoder {
    int getMemoryUsage();
    InputStream getInputStream(InputStream in);
}
