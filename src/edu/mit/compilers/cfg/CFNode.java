package edu.mit.compilers.cfg;

import java.util.Set;

public interface CFNode {

    /** set of LOCAL AssemblyVariables or temps that are used in the node*/
    Set<AssemblyVariable> getUsed();
    /** set of LOCAL AssemblyVariables or temps that are defined in the node*/
    Set<AssemblyVariable> getDefined();

    void setRegister();
}
