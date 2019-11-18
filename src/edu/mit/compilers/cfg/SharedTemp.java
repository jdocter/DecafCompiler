package edu.mit.compilers.cfg;

import edu.mit.compilers.inter.VariableTable;
import edu.mit.compilers.util.UIDObject;

public class SharedTemp extends UIDObject implements AssemblyVariable {

        public static final int TEMP_SIZE = 8;

        private final String name;
        private boolean offsetDeclared = false;
        private long offset;

        public SharedTemp() {
            name = "shared_t"+getUID();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getGlobalLabel(VariableTable variableTable) {
            return null;
        }

        @Override
        public long getArrayLength(VariableTable variableTable) {
            throw new RuntimeException("Temps don't have lengths");
        }

        @Override
        public int getElementSize(VariableTable variableTable) {
            return getElementSize();
        }
        public int getElementSize() {
        return 8;
    }

        public void setOffset(long offset) {
            this.offset = offset;
            offsetDeclared = true;
        }

        public long getOffset() {
            if (!offsetDeclared) throw new RuntimeException("stack offset must be set before it can be accessed");
            return this.offset;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Temp && super.equals(obj);
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public long getStackOffset(VariableTable variableTable) {
            if (!offsetDeclared) throw new RuntimeException("stack offset must be set before it can be accessed");
            return this.offset;
        }

        @Override
        public boolean isGlobal(VariableTable variableTable) {
            return false;
        }

        @Override
        public boolean isArray(VariableTable variableTable) {
            return false;
        }

        @Override
        public boolean isTemporary() {
            return false;
        }
}
