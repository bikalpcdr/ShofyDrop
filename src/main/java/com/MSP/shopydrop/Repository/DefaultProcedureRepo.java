package com.MSP.shopydrop.Repository;

import java.util.List;

public interface DefaultProcedureRepo {
    <T> List<T> getWithType(String pname, Object[][] params, Class<T> type);
    Object[] executeWithType(String pname, Object[][] params);
}
