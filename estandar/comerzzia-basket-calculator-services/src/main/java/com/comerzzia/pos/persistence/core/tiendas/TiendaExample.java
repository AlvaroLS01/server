/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */
package com.comerzzia.pos.persistence.core.tiendas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TiendaExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_CODALM = "CODALM";

    public static final String ORDER_BY_CODALM_DESC = "CODALM DESC";

    public static final String ORDER_BY_ID_TIPO_TIENDA = "ID_TIPO_TIENDA";

    public static final String ORDER_BY_ID_TIPO_TIENDA_DESC = "ID_TIPO_TIENDA DESC";

    public static final String ORDER_BY_VERSION_ARTICULOS_APL = "VERSION_ARTICULOS_APL";

    public static final String ORDER_BY_VERSION_ARTICULOS_APL_DESC = "VERSION_ARTICULOS_APL DESC";

    public static final String ORDER_BY_VERSION_ARTICULOS_REV = "VERSION_ARTICULOS_REV";

    public static final String ORDER_BY_VERSION_ARTICULOS_REV_DESC = "VERSION_ARTICULOS_REV DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";

    public static final String ORDER_BY_FECHA_VERSION_ARTICULOS_APL = "FECHA_VERSION_ARTICULOS_APL";

    public static final String ORDER_BY_FECHA_VERSION_ARTICULOS_APL_DESC = "FECHA_VERSION_ARTICULOS_APL DESC";

    public static final String ORDER_BY_FECHA_VERSION_ARTICULOS_REV = "FECHA_VERSION_ARTICULOS_REV";

    public static final String ORDER_BY_FECHA_VERSION_ARTICULOS_REV_DESC = "FECHA_VERSION_ARTICULOS_REV DESC";

    public static final String ORDER_BY_CODMEDPAG_POR_DEFECTO = "CODMEDPAG_POR_DEFECTO";

    public static final String ORDER_BY_CODMEDPAG_POR_DEFECTO_DESC = "CODMEDPAG_POR_DEFECTO DESC";

    public static final String ORDER_BY_CODCONALM_VENTAS = "CODCONALM_VENTAS";

    public static final String ORDER_BY_CODCONALM_VENTAS_DESC = "CODCONALM_VENTAS DESC";

    public static final String ORDER_BY_VERSION_CATEGORIZACIONES = "VERSION_CATEGORIZACIONES";

    public static final String ORDER_BY_VERSION_CATEGORIZACIONES_DESC = "VERSION_CATEGORIZACIONES DESC";

    public static final String ORDER_BY_ID_ACT_VERSION_ARTICULOS_APL = "ID_ACT_VERSION_ARTICULOS_APL";

    public static final String ORDER_BY_ID_ACT_VERSION_ARTICULOS_APL_DESC = "ID_ACT_VERSION_ARTICULOS_APL DESC";

    public static final String ORDER_BY_ID_ACT_VERSION_ARTICULOS_REV = "ID_ACT_VERSION_ARTICULOS_REV";

    public static final String ORDER_BY_ID_ACT_VERSION_ARTICULOS_REV_DESC = "ID_ACT_VERSION_ARTICULOS_REV DESC";

    public static final String ORDER_BY_CODMEDPAG_APARTADO = "CODMEDPAG_APARTADO";

    public static final String ORDER_BY_CODMEDPAG_APARTADO_DESC = "CODMEDPAG_APARTADO DESC";

    public static final String ORDER_BY_CODMEDPAG_PROMOCION = "CODMEDPAG_PROMOCION";

    public static final String ORDER_BY_CODMEDPAG_PROMOCION_DESC = "CODMEDPAG_PROMOCION DESC";

    public static final String ORDER_BY_CONFIGURACION = "CONFIGURACION";

    public static final String ORDER_BY_CONFIGURACION_DESC = "CONFIGURACION DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TiendaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> activoCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            activoCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getActivoCriteria() {
            return activoCriteria;
        }

        protected void addActivoCriterion(String condition, Object value, String property) {
            if (value != null) {
                activoCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addActivoCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                activoCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public boolean isValid() {
            return criteria.size() > 0
                || activoCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(activoCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition != null) {
                criteria.add(new Criterion(condition));
                allCriteria = null;
            }
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value != null) {
                criteria.add(new Criterion(condition, value));
                allCriteria = null;
            }
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 != null && value2 != null) {
                criteria.add(new Criterion(condition, value1, value2));
                allCriteria = null;
            }
        }

        public Criteria andUidActividadIsNull() {
            addCriterion("UID_ACTIVIDAD is null");
            return (Criteria) this;
        }

        public Criteria andUidActividadIsNotNull() {
            addCriterion("UID_ACTIVIDAD is not null");
            return (Criteria) this;
        }

        public Criteria andUidActividadEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD =", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD <>", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadGreaterThan(String value) {
            addCriterion("UID_ACTIVIDAD >", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadGreaterThanOrEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD >=", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadLessThan(String value) {
            addCriterion("UID_ACTIVIDAD <", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadLessThanOrEqualTo(String value) {
            addCriterion("UID_ACTIVIDAD <=", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadLike(String value) {
            addCriterion("UID_ACTIVIDAD like", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotLike(String value) {
            addCriterion("UID_ACTIVIDAD not like", value, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadIn(List<String> values) {
            addCriterion("UID_ACTIVIDAD in", values, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotIn(List<String> values) {
            addCriterion("UID_ACTIVIDAD not in", values, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadBetween(String value1, String value2) {
            addCriterion("UID_ACTIVIDAD between", value1, value2, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andUidActividadNotBetween(String value1, String value2) {
            addCriterion("UID_ACTIVIDAD not between", value1, value2, "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenIsNull() {
            addCriterion("CODALM is null");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenIsNotNull() {
            addCriterion("CODALM is not null");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenEqualTo(String value) {
            addCriterion("CODALM =", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotEqualTo(String value) {
            addCriterion("CODALM <>", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenGreaterThan(String value) {
            addCriterion("CODALM >", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenGreaterThanOrEqualTo(String value) {
            addCriterion("CODALM >=", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLessThan(String value) {
            addCriterion("CODALM <", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLessThanOrEqualTo(String value) {
            addCriterion("CODALM <=", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLike(String value) {
            addCriterion("CODALM like", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotLike(String value) {
            addCriterion("CODALM not like", value, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenIn(List<String> values) {
            addCriterion("CODALM in", values, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotIn(List<String> values) {
            addCriterion("CODALM not in", values, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenBetween(String value1, String value2) {
            addCriterion("CODALM between", value1, value2, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenNotBetween(String value1, String value2) {
            addCriterion("CODALM not between", value1, value2, "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaIsNull() {
            addCriterion("ID_TIPO_TIENDA is null");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaIsNotNull() {
            addCriterion("ID_TIPO_TIENDA is not null");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaEqualTo(Long value) {
            addCriterion("ID_TIPO_TIENDA =", value, "idTipoTienda");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaNotEqualTo(Long value) {
            addCriterion("ID_TIPO_TIENDA <>", value, "idTipoTienda");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaGreaterThan(Long value) {
            addCriterion("ID_TIPO_TIENDA >", value, "idTipoTienda");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_TIPO_TIENDA >=", value, "idTipoTienda");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaLessThan(Long value) {
            addCriterion("ID_TIPO_TIENDA <", value, "idTipoTienda");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaLessThanOrEqualTo(Long value) {
            addCriterion("ID_TIPO_TIENDA <=", value, "idTipoTienda");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaIn(List<Long> values) {
            addCriterion("ID_TIPO_TIENDA in", values, "idTipoTienda");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaNotIn(List<Long> values) {
            addCriterion("ID_TIPO_TIENDA not in", values, "idTipoTienda");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaBetween(Long value1, Long value2) {
            addCriterion("ID_TIPO_TIENDA between", value1, value2, "idTipoTienda");
            return (Criteria) this;
        }

        public Criteria andIdTipoTiendaNotBetween(Long value1, Long value2) {
            addCriterion("ID_TIPO_TIENDA not between", value1, value2, "idTipoTienda");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosIsNull() {
            addCriterion("VERSION_ARTICULOS is null");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosIsNotNull() {
            addCriterion("VERSION_ARTICULOS is not null");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosEqualTo(Long value) {
            addCriterion("VERSION_ARTICULOS =", value, "versionArticulos");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosNotEqualTo(Long value) {
            addCriterion("VERSION_ARTICULOS <>", value, "versionArticulos");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosGreaterThan(Long value) {
            addCriterion("VERSION_ARTICULOS >", value, "versionArticulos");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosGreaterThanOrEqualTo(Long value) {
            addCriterion("VERSION_ARTICULOS >=", value, "versionArticulos");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosLessThan(Long value) {
            addCriterion("VERSION_ARTICULOS <", value, "versionArticulos");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosLessThanOrEqualTo(Long value) {
            addCriterion("VERSION_ARTICULOS <=", value, "versionArticulos");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosIn(List<Long> values) {
            addCriterion("VERSION_ARTICULOS in", values, "versionArticulos");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosNotIn(List<Long> values) {
            addCriterion("VERSION_ARTICULOS not in", values, "versionArticulos");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosBetween(Long value1, Long value2) {
            addCriterion("VERSION_ARTICULOS between", value1, value2, "versionArticulos");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosNotBetween(Long value1, Long value2) {
            addCriterion("VERSION_ARTICULOS not between", value1, value2, "versionArticulos");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevIsNull() {
            addCriterion("VERSION_ARTICULOS_REV is null");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevIsNotNull() {
            addCriterion("VERSION_ARTICULOS_REV is not null");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevEqualTo(Long value) {
            addCriterion("VERSION_ARTICULOS_REV =", value, "versionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevNotEqualTo(Long value) {
            addCriterion("VERSION_ARTICULOS_REV <>", value, "versionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevGreaterThan(Long value) {
            addCriterion("VERSION_ARTICULOS_REV >", value, "versionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevGreaterThanOrEqualTo(Long value) {
            addCriterion("VERSION_ARTICULOS_REV >=", value, "versionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevLessThan(Long value) {
            addCriterion("VERSION_ARTICULOS_REV <", value, "versionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevLessThanOrEqualTo(Long value) {
            addCriterion("VERSION_ARTICULOS_REV <=", value, "versionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevIn(List<Long> values) {
            addCriterion("VERSION_ARTICULOS_REV in", values, "versionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevNotIn(List<Long> values) {
            addCriterion("VERSION_ARTICULOS_REV not in", values, "versionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevBetween(Long value1, Long value2) {
            addCriterion("VERSION_ARTICULOS_REV between", value1, value2, "versionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andVersionArticulosRevNotBetween(Long value1, Long value2) {
            addCriterion("VERSION_ARTICULOS_REV not between", value1, value2, "versionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andActivoIsNull() {
            addCriterion("ACTIVO is null");
            return (Criteria) this;
        }

        public Criteria andActivoIsNotNull() {
            addCriterion("ACTIVO is not null");
            return (Criteria) this;
        }

        public Criteria andActivoEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO =", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO <>", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoGreaterThan(Boolean value) {
            addActivoCriterion("ACTIVO >", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoGreaterThanOrEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO >=", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoLessThan(Boolean value) {
            addActivoCriterion("ACTIVO <", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoLessThanOrEqualTo(Boolean value) {
            addActivoCriterion("ACTIVO <=", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoLike(Boolean value) {
            addActivoCriterion("ACTIVO like", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotLike(Boolean value) {
            addActivoCriterion("ACTIVO not like", value, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoIn(List<Boolean> values) {
            addActivoCriterion("ACTIVO in", values, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotIn(List<Boolean> values) {
            addActivoCriterion("ACTIVO not in", values, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoBetween(Boolean value1, Boolean value2) {
            addActivoCriterion("ACTIVO between", value1, value2, "activo");
            return (Criteria) this;
        }

        public Criteria andActivoNotBetween(Boolean value1, Boolean value2) {
            addActivoCriterion("ACTIVO not between", value1, value2, "activo");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosIsNull() {
            addCriterion("FECHA_VERSION_ARTICULOS is null");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosIsNotNull() {
            addCriterion("FECHA_VERSION_ARTICULOS is not null");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosEqualTo(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS =", value, "fechaVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosNotEqualTo(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS <>", value, "fechaVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosGreaterThan(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS >", value, "fechaVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS >=", value, "fechaVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosLessThan(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS <", value, "fechaVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS <=", value, "fechaVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosIn(List<Date> values) {
            addCriterion("FECHA_VERSION_ARTICULOS in", values, "fechaVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosNotIn(List<Date> values) {
            addCriterion("FECHA_VERSION_ARTICULOS not in", values, "fechaVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosBetween(Date value1, Date value2) {
            addCriterion("FECHA_VERSION_ARTICULOS between", value1, value2, "fechaVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_VERSION_ARTICULOS not between", value1, value2, "fechaVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevIsNull() {
            addCriterion("FECHA_VERSION_ARTICULOS_REV is null");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevIsNotNull() {
            addCriterion("FECHA_VERSION_ARTICULOS_REV is not null");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevEqualTo(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS_REV =", value, "fechaVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevNotEqualTo(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS_REV <>", value, "fechaVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevGreaterThan(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS_REV >", value, "fechaVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS_REV >=", value, "fechaVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevLessThan(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS_REV <", value, "fechaVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_VERSION_ARTICULOS_REV <=", value, "fechaVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevIn(List<Date> values) {
            addCriterion("FECHA_VERSION_ARTICULOS_REV in", values, "fechaVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevNotIn(List<Date> values) {
            addCriterion("FECHA_VERSION_ARTICULOS_REV not in", values, "fechaVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevBetween(Date value1, Date value2) {
            addCriterion("FECHA_VERSION_ARTICULOS_REV between", value1, value2, "fechaVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andFechaVersionArticulosRevNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_VERSION_ARTICULOS_REV not between", value1, value2, "fechaVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoIsNull() {
            addCriterion("CODMEDPAG_POR_DEFECTO is null");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoIsNotNull() {
            addCriterion("CODMEDPAG_POR_DEFECTO is not null");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoEqualTo(String value) {
            addCriterion("CODMEDPAG_POR_DEFECTO =", value, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoNotEqualTo(String value) {
            addCriterion("CODMEDPAG_POR_DEFECTO <>", value, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoGreaterThan(String value) {
            addCriterion("CODMEDPAG_POR_DEFECTO >", value, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoGreaterThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG_POR_DEFECTO >=", value, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoLessThan(String value) {
            addCriterion("CODMEDPAG_POR_DEFECTO <", value, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoLessThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG_POR_DEFECTO <=", value, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoLike(String value) {
            addCriterion("CODMEDPAG_POR_DEFECTO like", value, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoNotLike(String value) {
            addCriterion("CODMEDPAG_POR_DEFECTO not like", value, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoIn(List<String> values) {
            addCriterion("CODMEDPAG_POR_DEFECTO in", values, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoNotIn(List<String> values) {
            addCriterion("CODMEDPAG_POR_DEFECTO not in", values, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoBetween(String value1, String value2) {
            addCriterion("CODMEDPAG_POR_DEFECTO between", value1, value2, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoNotBetween(String value1, String value2) {
            addCriterion("CODMEDPAG_POR_DEFECTO not between", value1, value2, "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasIsNull() {
            addCriterion("CODCONALM_VENTAS is null");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasIsNotNull() {
            addCriterion("CODCONALM_VENTAS is not null");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasEqualTo(String value) {
            addCriterion("CODCONALM_VENTAS =", value, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasNotEqualTo(String value) {
            addCriterion("CODCONALM_VENTAS <>", value, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasGreaterThan(String value) {
            addCriterion("CODCONALM_VENTAS >", value, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasGreaterThanOrEqualTo(String value) {
            addCriterion("CODCONALM_VENTAS >=", value, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasLessThan(String value) {
            addCriterion("CODCONALM_VENTAS <", value, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasLessThanOrEqualTo(String value) {
            addCriterion("CODCONALM_VENTAS <=", value, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasLike(String value) {
            addCriterion("CODCONALM_VENTAS like", value, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasNotLike(String value) {
            addCriterion("CODCONALM_VENTAS not like", value, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasIn(List<String> values) {
            addCriterion("CODCONALM_VENTAS in", values, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasNotIn(List<String> values) {
            addCriterion("CODCONALM_VENTAS not in", values, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasBetween(String value1, String value2) {
            addCriterion("CODCONALM_VENTAS between", value1, value2, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasNotBetween(String value1, String value2) {
            addCriterion("CODCONALM_VENTAS not between", value1, value2, "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesIsNull() {
            addCriterion("VERSION_CATEGORIZACIONES is null");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesIsNotNull() {
            addCriterion("VERSION_CATEGORIZACIONES is not null");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesEqualTo(Long value) {
            addCriterion("VERSION_CATEGORIZACIONES =", value, "versionCategorizaciones");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesNotEqualTo(Long value) {
            addCriterion("VERSION_CATEGORIZACIONES <>", value, "versionCategorizaciones");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesGreaterThan(Long value) {
            addCriterion("VERSION_CATEGORIZACIONES >", value, "versionCategorizaciones");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesGreaterThanOrEqualTo(Long value) {
            addCriterion("VERSION_CATEGORIZACIONES >=", value, "versionCategorizaciones");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesLessThan(Long value) {
            addCriterion("VERSION_CATEGORIZACIONES <", value, "versionCategorizaciones");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesLessThanOrEqualTo(Long value) {
            addCriterion("VERSION_CATEGORIZACIONES <=", value, "versionCategorizaciones");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesIn(List<Long> values) {
            addCriterion("VERSION_CATEGORIZACIONES in", values, "versionCategorizaciones");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesNotIn(List<Long> values) {
            addCriterion("VERSION_CATEGORIZACIONES not in", values, "versionCategorizaciones");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesBetween(Long value1, Long value2) {
            addCriterion("VERSION_CATEGORIZACIONES between", value1, value2, "versionCategorizaciones");
            return (Criteria) this;
        }

        public Criteria andVersionCategorizacionesNotBetween(Long value1, Long value2) {
            addCriterion("VERSION_CATEGORIZACIONES not between", value1, value2, "versionCategorizaciones");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosIsNull() {
            addCriterion("ID_ACT_VERSION_ARTICULOS is null");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosIsNotNull() {
            addCriterion("ID_ACT_VERSION_ARTICULOS is not null");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosEqualTo(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS =", value, "idActVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosNotEqualTo(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS <>", value, "idActVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosGreaterThan(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS >", value, "idActVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS >=", value, "idActVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosLessThan(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS <", value, "idActVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosLessThanOrEqualTo(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS <=", value, "idActVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosIn(List<Long> values) {
            addCriterion("ID_ACT_VERSION_ARTICULOS in", values, "idActVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosNotIn(List<Long> values) {
            addCriterion("ID_ACT_VERSION_ARTICULOS not in", values, "idActVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosBetween(Long value1, Long value2) {
            addCriterion("ID_ACT_VERSION_ARTICULOS between", value1, value2, "idActVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosNotBetween(Long value1, Long value2) {
            addCriterion("ID_ACT_VERSION_ARTICULOS not between", value1, value2, "idActVersionArticulos");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevIsNull() {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV is null");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevIsNotNull() {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV is not null");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevEqualTo(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV =", value, "idActVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevNotEqualTo(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV <>", value, "idActVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevGreaterThan(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV >", value, "idActVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV >=", value, "idActVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevLessThan(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV <", value, "idActVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevLessThanOrEqualTo(Long value) {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV <=", value, "idActVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevIn(List<Long> values) {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV in", values, "idActVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevNotIn(List<Long> values) {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV not in", values, "idActVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevBetween(Long value1, Long value2) {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV between", value1, value2, "idActVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andIdActVersionArticulosRevNotBetween(Long value1, Long value2) {
            addCriterion("ID_ACT_VERSION_ARTICULOS_REV not between", value1, value2, "idActVersionArticulosRev");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoIsNull() {
            addCriterion("CODMEDPAG_APARTADO is null");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoIsNotNull() {
            addCriterion("CODMEDPAG_APARTADO is not null");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoEqualTo(String value) {
            addCriterion("CODMEDPAG_APARTADO =", value, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoNotEqualTo(String value) {
            addCriterion("CODMEDPAG_APARTADO <>", value, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoGreaterThan(String value) {
            addCriterion("CODMEDPAG_APARTADO >", value, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoGreaterThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG_APARTADO >=", value, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoLessThan(String value) {
            addCriterion("CODMEDPAG_APARTADO <", value, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoLessThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG_APARTADO <=", value, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoLike(String value) {
            addCriterion("CODMEDPAG_APARTADO like", value, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoNotLike(String value) {
            addCriterion("CODMEDPAG_APARTADO not like", value, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoIn(List<String> values) {
            addCriterion("CODMEDPAG_APARTADO in", values, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoNotIn(List<String> values) {
            addCriterion("CODMEDPAG_APARTADO not in", values, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoBetween(String value1, String value2) {
            addCriterion("CODMEDPAG_APARTADO between", value1, value2, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoNotBetween(String value1, String value2) {
            addCriterion("CODMEDPAG_APARTADO not between", value1, value2, "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionIsNull() {
            addCriterion("CODMEDPAG_PROMOCION is null");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionIsNotNull() {
            addCriterion("CODMEDPAG_PROMOCION is not null");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionEqualTo(String value) {
            addCriterion("CODMEDPAG_PROMOCION =", value, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionNotEqualTo(String value) {
            addCriterion("CODMEDPAG_PROMOCION <>", value, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionGreaterThan(String value) {
            addCriterion("CODMEDPAG_PROMOCION >", value, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionGreaterThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG_PROMOCION >=", value, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionLessThan(String value) {
            addCriterion("CODMEDPAG_PROMOCION <", value, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionLessThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG_PROMOCION <=", value, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionLike(String value) {
            addCriterion("CODMEDPAG_PROMOCION like", value, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionNotLike(String value) {
            addCriterion("CODMEDPAG_PROMOCION not like", value, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionIn(List<String> values) {
            addCriterion("CODMEDPAG_PROMOCION in", values, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionNotIn(List<String> values) {
            addCriterion("CODMEDPAG_PROMOCION not in", values, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionBetween(String value1, String value2) {
            addCriterion("CODMEDPAG_PROMOCION between", value1, value2, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionNotBetween(String value1, String value2) {
            addCriterion("CODMEDPAG_PROMOCION not between", value1, value2, "codMedioPagoPromocion");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodAlmacenLikeInsensitive(String value) {
            addCriterion("upper(CODALM) like", value.toUpperCase(), "codAlmacen");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPorDefectoLikeInsensitive(String value) {
            addCriterion("upper(CODMEDPAG_POR_DEFECTO) like", value.toUpperCase(), "codMedioPagoPorDefecto");
            return (Criteria) this;
        }

        public Criteria andCodConceptoAlmacenVentasLikeInsensitive(String value) {
            addCriterion("upper(CODCONALM_VENTAS) like", value.toUpperCase(), "codConceptoAlmacenVentas");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoApartadoLikeInsensitive(String value) {
            addCriterion("upper(CODMEDPAG_APARTADO) like", value.toUpperCase(), "codMedioPagoApartado");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoPromocionLikeInsensitive(String value) {
            addCriterion("upper(CODMEDPAG_PROMOCION) like", value.toUpperCase(), "codMedioPagoPromocion");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}