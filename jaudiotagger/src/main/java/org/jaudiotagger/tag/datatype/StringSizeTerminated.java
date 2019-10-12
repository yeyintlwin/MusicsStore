/**
 * @author : Paul Taylor
 * @author : Eric Farng
 * <p>
 * Version @version:$Id$
 * <p>
 * MusicTag Copyright (C)2003,2004
 * <p>
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public  License as published by the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not,
 * you can get a copy from http://www.opensource.org/licenses/lgpl-license.php or write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * <p>
 * Description:
 * Contains a string which is NOT null terminated.
 * Warning this datatype type can only be used as the last datatype in a frame because
 * it reads the remainder of the frame as there is no null terminated or provision
 * for setting a defined size.
 */
package org.jaudiotagger.tag.datatype;

import org.jaudiotagger.tag.id3.AbstractTagFrameBody;
import org.jaudiotagger.tag.id3.valuepair.TextEncoding;

/**
 * Represents a String which is not delimited by null character with fixed text encoding.
 * <p>
 * This type of String will usually only be used when it is the last field within a frame, when reading the remainder of the byte array will
 * be read, when writing the frame will accommodate the required size for the String. The String will be encoded
 * using the default encoding regardless of what encoding may be specified in the framebody
 */
public class StringSizeTerminated extends TextEncodedStringSizeTerminated {

    /**
     * Creates a new ObjectStringSizeTerminated datatype.
     *
     * @param identifier identifies the frame type
     * @param frameBody
     */
    public StringSizeTerminated(String identifier, AbstractTagFrameBody frameBody) {
        super(identifier, frameBody);
    }

    public StringSizeTerminated(StringSizeTerminated object) {
        super(object);
    }

    public boolean equals(Object obj) {
        return obj instanceof StringSizeTerminated && super.equals(obj);
    }

    protected String getTextEncodingCharSet() {
        return TextEncoding.CHARSET_ISO_8859_1;
    }
}
