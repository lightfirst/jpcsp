/*
This file is part of jpcsp.

Jpcsp is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jpcsp is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Jpcsp.  If not, see <http://www.gnu.org/licenses/>.
 */
package jpcsp.HLE.modules;

import static jpcsp.util.Utilities.readUnaligned32;

import org.apache.log4j.Logger;

import jpcsp.HLE.BufferInfo;
import jpcsp.HLE.BufferInfo.LengthInfo;
import jpcsp.HLE.BufferInfo.Usage;
import jpcsp.HLE.HLEFunction;
import jpcsp.HLE.HLEModule;
import jpcsp.HLE.HLEUnimplemented;
import jpcsp.HLE.Modules;
import jpcsp.HLE.TPointer;
import jpcsp.HLE.TPointer32;
import jpcsp.crypto.CryptoEngine;
import jpcsp.crypto.PRX;
import jpcsp.memory.IMemoryReader;
import jpcsp.memory.IMemoryWriter;
import jpcsp.memory.MemoryReader;
import jpcsp.memory.MemoryWriter;
import jpcsp.util.Utilities;

public class sceMesgd extends HLEModule {
    public static Logger log = Modules.getLogger("sceMesgd");
    private PRX prxEngine = new CryptoEngine().getPRXEngine();

    public int hleMesgd_driver_102DC8AF(byte[] buffer, int bufferOffset, int bufferSize, TPointer32 resultSizeAddr) {
    	if (log.isTraceEnabled()) {
    		log.trace(String.format("hleMesgd_driver_102DC8AF input(size=0x%X): %s", bufferSize, Utilities.getMemoryDump(buffer, bufferOffset, bufferSize)));
    	}

        int tag = readUnaligned32(buffer, bufferOffset + 0xD0);
        int type;
        switch (tag) {
        	case 0x0E000000:
        		type = 0;
        		break;
        	case 0x63BAB403:
        	case 0xD8231EF0:
        	case 0xD82310F0:
        	case 0xD82328F0:
        		type = 2;
        		break;
    		default:
    			return -301;
        }
    	int result = prxEngine.DecryptPRX(buffer, bufferSize, type, null, null);

    	int resultSize = 0;
    	if (result > 0) {
    		resultSize = result;
    		result = 0;
    	}
    	resultSizeAddr.setValue(resultSize);

    	if (log.isTraceEnabled()) {
    		log.trace(String.format("hleMesgd_driver_102DC8AF result=0x%X, output(size=0x%X): %s", result, resultSize, Utilities.getMemoryDump(buffer, bufferOffset, resultSize)));
    	}

    	return result;
    }

    @HLEFunction(nid = 0x102DC8AF, version = 150)
    public int sceMesgd_driver_102DC8AF(@BufferInfo(lengthInfo=LengthInfo.nextParameter, usage=Usage.inout) TPointer buffer, int bufferSize, @BufferInfo(usage=Usage.out) TPointer32 resultSizeAddr) {
    	byte[] bytes = new byte[bufferSize];
    	IMemoryReader memoryReader = MemoryReader.getMemoryReader(buffer, bufferSize, 1);
    	for (int i = 0; i < bufferSize; i++) {
    		bytes[i] = (byte) memoryReader.readNext();
    	}

    	int result = hleMesgd_driver_102DC8AF(bytes, 0, bufferSize, resultSizeAddr);
    	if (result != 0) {
    		return result;
    	}

    	int resultSize = resultSizeAddr.getValue();
    	IMemoryWriter memoryWriter = MemoryWriter.getMemoryWriter(buffer, resultSize, 1);
    	for (int i = 0; i < resultSize; i++) {
    		memoryWriter.writeNext(bytes[i]);
    	}
    	memoryWriter.flush();

    	return 0;
    }

    @HLEUnimplemented
    @HLEFunction(nid = 0xADD0CB66, version = 150)
    public int sceMesgd_driver_ADD0CB66() {
    	return 0;
    }

    /**
     * Used to decrypt meimg.img
     */
    @HLEUnimplemented
    @HLEFunction(nid = 0x7A0E484C, version = 150)
    public int sceWmd_driver_7A0E484C(@BufferInfo(lengthInfo=LengthInfo.nextParameter, usage=Usage.inout) TPointer buffer, int bufferSize, @BufferInfo(usage=Usage.out) TPointer32 resultSizeAddr) {
    	return 0;
    }
}
