package com.comerzzia.pos.persistence.mediosPagos;

import java.util.ArrayList;
import java.util.List;

public class MedioPagoExample {
    public static final String ORDER_BY_UID_ACTIVIDAD = "UID_ACTIVIDAD";

    public static final String ORDER_BY_UID_ACTIVIDAD_DESC = "UID_ACTIVIDAD DESC";

    public static final String ORDER_BY_CODMEDPAG = "CODMEDPAG";

    public static final String ORDER_BY_CODMEDPAG_DESC = "CODMEDPAG DESC";

    public static final String ORDER_BY_DESMEDPAG = "DESMEDPAG";

    public static final String ORDER_BY_DESMEDPAG_DESC = "DESMEDPAG DESC";

    public static final String ORDER_BY_CONTADO = "CONTADO";

    public static final String ORDER_BY_CONTADO_DESC = "CONTADO DESC";

    public static final String ORDER_BY_EFECTIVO = "EFECTIVO";

    public static final String ORDER_BY_EFECTIVO_DESC = "EFECTIVO DESC";

    public static final String ORDER_BY_TARJETA_CREDITO = "TARJETA_CREDITO";

    public static final String ORDER_BY_TARJETA_CREDITO_DESC = "TARJETA_CREDITO DESC";

    public static final String ORDER_BY_CODTIPOEFEC = "CODTIPOEFEC";

    public static final String ORDER_BY_CODTIPOEFEC_DESC = "CODTIPOEFEC DESC";

    public static final String ORDER_BY_VISIBLE_VENTA = "VISIBLE_VENTA";

    public static final String ORDER_BY_VISIBLE_VENTA_DESC = "VISIBLE_VENTA DESC";

    public static final String ORDER_BY_VISIBLE_TIENDA_VIRTUAL = "VISIBLE_TIENDA_VIRTUAL";

    public static final String ORDER_BY_VISIBLE_TIENDA_VIRTUAL_DESC = "VISIBLE_TIENDA_VIRTUAL DESC";

    public static final String ORDER_BY_VISIBLE_COMPRA = "VISIBLE_COMPRA";

    public static final String ORDER_BY_VISIBLE_COMPRA_DESC = "VISIBLE_COMPRA DESC";

    public static final String ORDER_BY_ACTIVO = "ACTIVO";

    public static final String ORDER_BY_ACTIVO_DESC = "ACTIVO DESC";

    public static final String ORDER_BY_MANUAL = "MANUAL";

    public static final String ORDER_BY_MANUAL_DESC = "MANUAL DESC";

    public static final String ORDER_BY_RECUENTO_AUTOMATICO_CAJA = "RECUENTO_AUTOMATICO_CAJA";

    public static final String ORDER_BY_RECUENTO_AUTOMATICO_CAJA_DESC = "RECUENTO_AUTOMATICO_CAJA DESC";

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MedioPagoExample() {
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
        protected List<Criterion> contadoCriteria;

        protected List<Criterion> efectivoCriteria;

        protected List<Criterion> tarjetaCreditoCriteria;

        protected List<Criterion> visibleVentaCriteria;

        protected List<Criterion> visibleTiendaVirtualCriteria;

        protected List<Criterion> visibleCompraCriteria;

        protected List<Criterion> activoCriteria;

        protected List<Criterion> manualCriteria;

        protected List<Criterion> recuentoAutomaticoCajaCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            contadoCriteria = new ArrayList<Criterion>();
            efectivoCriteria = new ArrayList<Criterion>();
            tarjetaCreditoCriteria = new ArrayList<Criterion>();
            visibleVentaCriteria = new ArrayList<Criterion>();
            visibleTiendaVirtualCriteria = new ArrayList<Criterion>();
            visibleCompraCriteria = new ArrayList<Criterion>();
            activoCriteria = new ArrayList<Criterion>();
            manualCriteria = new ArrayList<Criterion>();
            recuentoAutomaticoCajaCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getContadoCriteria() {
            return contadoCriteria;
        }

        protected void addContadoCriterion(String condition, Object value, String property) {
            if (value != null) {
                contadoCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addContadoCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                contadoCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public List<Criterion> getEfectivoCriteria() {
            return efectivoCriteria;
        }

        protected void addEfectivoCriterion(String condition, Object value, String property) {
            if (value != null) {
                efectivoCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addEfectivoCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                efectivoCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public List<Criterion> getTarjetaCreditoCriteria() {
            return tarjetaCreditoCriteria;
        }

        protected void addTarjetaCreditoCriterion(String condition, Object value, String property) {
            if (value != null) {
                tarjetaCreditoCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addTarjetaCreditoCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                tarjetaCreditoCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public List<Criterion> getVisibleVentaCriteria() {
            return visibleVentaCriteria;
        }

        protected void addVisibleVentaCriterion(String condition, Object value, String property) {
            if (value != null) {
                visibleVentaCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addVisibleVentaCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                visibleVentaCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public List<Criterion> getVisibleTiendaVirtualCriteria() {
            return visibleTiendaVirtualCriteria;
        }

        protected void addVisibleTiendaVirtualCriterion(String condition, Object value, String property) {
            if (value != null) {
                visibleTiendaVirtualCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addVisibleTiendaVirtualCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                visibleTiendaVirtualCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public List<Criterion> getVisibleCompraCriteria() {
            return visibleCompraCriteria;
        }

        protected void addVisibleCompraCriterion(String condition, Object value, String property) {
            if (value != null) {
                visibleCompraCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addVisibleCompraCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                visibleCompraCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
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

        public List<Criterion> getManualCriteria() {
            return manualCriteria;
        }

        protected void addManualCriterion(String condition, Object value, String property) {
            if (value != null) {
                manualCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addManualCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                manualCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public List<Criterion> getRecuentoAutomaticoCajaCriteria() {
            return recuentoAutomaticoCajaCriteria;
        }

        protected void addRecuentoAutomaticoCajaCriterion(String condition, Object value, String property) {
            if (value != null) {
                recuentoAutomaticoCajaCriteria.add(new Criterion(condition, value, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        protected void addRecuentoAutomaticoCajaCriterion(String condition, Boolean value1, Boolean value2, String property) {
            if (value1 != null && value2 != null) {
                recuentoAutomaticoCajaCriteria.add(new Criterion(condition, value1, value2, "com.comerzzia.core.util.mybatis.typehandlers.BooleanStringTypeHandler"));
                allCriteria = null;
            }
        }

        public boolean isValid() {
            return criteria.size() > 0
                || contadoCriteria.size() > 0
                || efectivoCriteria.size() > 0
                || tarjetaCreditoCriteria.size() > 0
                || visibleVentaCriteria.size() > 0
                || visibleTiendaVirtualCriteria.size() > 0
                || visibleCompraCriteria.size() > 0
                || activoCriteria.size() > 0
                || manualCriteria.size() > 0
                || recuentoAutomaticoCajaCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(contadoCriteria);
                allCriteria.addAll(efectivoCriteria);
                allCriteria.addAll(tarjetaCreditoCriteria);
                allCriteria.addAll(visibleVentaCriteria);
                allCriteria.addAll(visibleTiendaVirtualCriteria);
                allCriteria.addAll(visibleCompraCriteria);
                allCriteria.addAll(activoCriteria);
                allCriteria.addAll(manualCriteria);
                allCriteria.addAll(recuentoAutomaticoCajaCriteria);
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

        public Criteria andCodMedioPagoIsNull() {
            addCriterion("CODMEDPAG is null");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoIsNotNull() {
            addCriterion("CODMEDPAG is not null");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoEqualTo(String value) {
            addCriterion("CODMEDPAG =", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoNotEqualTo(String value) {
            addCriterion("CODMEDPAG <>", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoGreaterThan(String value) {
            addCriterion("CODMEDPAG >", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoGreaterThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG >=", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoLessThan(String value) {
            addCriterion("CODMEDPAG <", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoLessThanOrEqualTo(String value) {
            addCriterion("CODMEDPAG <=", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoLike(String value) {
            addCriterion("CODMEDPAG like", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoNotLike(String value) {
            addCriterion("CODMEDPAG not like", value, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoIn(List<String> values) {
            addCriterion("CODMEDPAG in", values, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoNotIn(List<String> values) {
            addCriterion("CODMEDPAG not in", values, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoBetween(String value1, String value2) {
            addCriterion("CODMEDPAG between", value1, value2, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoNotBetween(String value1, String value2) {
            addCriterion("CODMEDPAG not between", value1, value2, "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoIsNull() {
            addCriterion("DESMEDPAG is null");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoIsNotNull() {
            addCriterion("DESMEDPAG is not null");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoEqualTo(String value) {
            addCriterion("DESMEDPAG =", value, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoNotEqualTo(String value) {
            addCriterion("DESMEDPAG <>", value, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoGreaterThan(String value) {
            addCriterion("DESMEDPAG >", value, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoGreaterThanOrEqualTo(String value) {
            addCriterion("DESMEDPAG >=", value, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoLessThan(String value) {
            addCriterion("DESMEDPAG <", value, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoLessThanOrEqualTo(String value) {
            addCriterion("DESMEDPAG <=", value, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoLike(String value) {
            addCriterion("DESMEDPAG like", value, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoNotLike(String value) {
            addCriterion("DESMEDPAG not like", value, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoIn(List<String> values) {
            addCriterion("DESMEDPAG in", values, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoNotIn(List<String> values) {
            addCriterion("DESMEDPAG not in", values, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoBetween(String value1, String value2) {
            addCriterion("DESMEDPAG between", value1, value2, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoNotBetween(String value1, String value2) {
            addCriterion("DESMEDPAG not between", value1, value2, "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andContadoIsNull() {
            addCriterion("CONTADO is null");
            return (Criteria) this;
        }

        public Criteria andContadoIsNotNull() {
            addCriterion("CONTADO is not null");
            return (Criteria) this;
        }

        public Criteria andContadoEqualTo(Boolean value) {
            addContadoCriterion("CONTADO =", value, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoNotEqualTo(Boolean value) {
            addContadoCriterion("CONTADO <>", value, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoGreaterThan(Boolean value) {
            addContadoCriterion("CONTADO >", value, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoGreaterThanOrEqualTo(Boolean value) {
            addContadoCriterion("CONTADO >=", value, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoLessThan(Boolean value) {
            addContadoCriterion("CONTADO <", value, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoLessThanOrEqualTo(Boolean value) {
            addContadoCriterion("CONTADO <=", value, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoLike(Boolean value) {
            addContadoCriterion("CONTADO like", value, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoNotLike(Boolean value) {
            addContadoCriterion("CONTADO not like", value, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoIn(List<Boolean> values) {
            addContadoCriterion("CONTADO in", values, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoNotIn(List<Boolean> values) {
            addContadoCriterion("CONTADO not in", values, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoBetween(Boolean value1, Boolean value2) {
            addContadoCriterion("CONTADO between", value1, value2, "contado");
            return (Criteria) this;
        }

        public Criteria andContadoNotBetween(Boolean value1, Boolean value2) {
            addContadoCriterion("CONTADO not between", value1, value2, "contado");
            return (Criteria) this;
        }

        public Criteria andEfectivoIsNull() {
            addCriterion("EFECTIVO is null");
            return (Criteria) this;
        }

        public Criteria andEfectivoIsNotNull() {
            addCriterion("EFECTIVO is not null");
            return (Criteria) this;
        }

        public Criteria andEfectivoEqualTo(Boolean value) {
            addEfectivoCriterion("EFECTIVO =", value, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoNotEqualTo(Boolean value) {
            addEfectivoCriterion("EFECTIVO <>", value, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoGreaterThan(Boolean value) {
            addEfectivoCriterion("EFECTIVO >", value, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoGreaterThanOrEqualTo(Boolean value) {
            addEfectivoCriterion("EFECTIVO >=", value, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoLessThan(Boolean value) {
            addEfectivoCriterion("EFECTIVO <", value, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoLessThanOrEqualTo(Boolean value) {
            addEfectivoCriterion("EFECTIVO <=", value, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoLike(Boolean value) {
            addEfectivoCriterion("EFECTIVO like", value, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoNotLike(Boolean value) {
            addEfectivoCriterion("EFECTIVO not like", value, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoIn(List<Boolean> values) {
            addEfectivoCriterion("EFECTIVO in", values, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoNotIn(List<Boolean> values) {
            addEfectivoCriterion("EFECTIVO not in", values, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoBetween(Boolean value1, Boolean value2) {
            addEfectivoCriterion("EFECTIVO between", value1, value2, "efectivo");
            return (Criteria) this;
        }

        public Criteria andEfectivoNotBetween(Boolean value1, Boolean value2) {
            addEfectivoCriterion("EFECTIVO not between", value1, value2, "efectivo");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoIsNull() {
            addCriterion("TARJETA_CREDITO is null");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoIsNotNull() {
            addCriterion("TARJETA_CREDITO is not null");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoEqualTo(Boolean value) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO =", value, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoNotEqualTo(Boolean value) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO <>", value, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoGreaterThan(Boolean value) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO >", value, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoGreaterThanOrEqualTo(Boolean value) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO >=", value, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoLessThan(Boolean value) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO <", value, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoLessThanOrEqualTo(Boolean value) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO <=", value, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoLike(Boolean value) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO like", value, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoNotLike(Boolean value) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO not like", value, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoIn(List<Boolean> values) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO in", values, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoNotIn(List<Boolean> values) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO not in", values, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoBetween(Boolean value1, Boolean value2) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO between", value1, value2, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andTarjetaCreditoNotBetween(Boolean value1, Boolean value2) {
            addTarjetaCreditoCriterion("TARJETA_CREDITO not between", value1, value2, "tarjetaCredito");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoIsNull() {
            addCriterion("CODTIPOEFEC is null");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoIsNotNull() {
            addCriterion("CODTIPOEFEC is not null");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoEqualTo(String value) {
            addCriterion("CODTIPOEFEC =", value, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoNotEqualTo(String value) {
            addCriterion("CODTIPOEFEC <>", value, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoGreaterThan(String value) {
            addCriterion("CODTIPOEFEC >", value, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoGreaterThanOrEqualTo(String value) {
            addCriterion("CODTIPOEFEC >=", value, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoLessThan(String value) {
            addCriterion("CODTIPOEFEC <", value, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoLessThanOrEqualTo(String value) {
            addCriterion("CODTIPOEFEC <=", value, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoLike(String value) {
            addCriterion("CODTIPOEFEC like", value, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoNotLike(String value) {
            addCriterion("CODTIPOEFEC not like", value, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoIn(List<String> values) {
            addCriterion("CODTIPOEFEC in", values, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoNotIn(List<String> values) {
            addCriterion("CODTIPOEFEC not in", values, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoBetween(String value1, String value2) {
            addCriterion("CODTIPOEFEC between", value1, value2, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoNotBetween(String value1, String value2) {
            addCriterion("CODTIPOEFEC not between", value1, value2, "codTipoEfecto");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaIsNull() {
            addCriterion("VISIBLE_VENTA is null");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaIsNotNull() {
            addCriterion("VISIBLE_VENTA is not null");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaEqualTo(Boolean value) {
            addVisibleVentaCriterion("VISIBLE_VENTA =", value, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaNotEqualTo(Boolean value) {
            addVisibleVentaCriterion("VISIBLE_VENTA <>", value, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaGreaterThan(Boolean value) {
            addVisibleVentaCriterion("VISIBLE_VENTA >", value, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaGreaterThanOrEqualTo(Boolean value) {
            addVisibleVentaCriterion("VISIBLE_VENTA >=", value, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaLessThan(Boolean value) {
            addVisibleVentaCriterion("VISIBLE_VENTA <", value, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaLessThanOrEqualTo(Boolean value) {
            addVisibleVentaCriterion("VISIBLE_VENTA <=", value, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaLike(Boolean value) {
            addVisibleVentaCriterion("VISIBLE_VENTA like", value, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaNotLike(Boolean value) {
            addVisibleVentaCriterion("VISIBLE_VENTA not like", value, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaIn(List<Boolean> values) {
            addVisibleVentaCriterion("VISIBLE_VENTA in", values, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaNotIn(List<Boolean> values) {
            addVisibleVentaCriterion("VISIBLE_VENTA not in", values, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaBetween(Boolean value1, Boolean value2) {
            addVisibleVentaCriterion("VISIBLE_VENTA between", value1, value2, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleVentaNotBetween(Boolean value1, Boolean value2) {
            addVisibleVentaCriterion("VISIBLE_VENTA not between", value1, value2, "visibleVenta");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualIsNull() {
            addCriterion("VISIBLE_TIENDA_VIRTUAL is null");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualIsNotNull() {
            addCriterion("VISIBLE_TIENDA_VIRTUAL is not null");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualEqualTo(Boolean value) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL =", value, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualNotEqualTo(Boolean value) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL <>", value, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualGreaterThan(Boolean value) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL >", value, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualGreaterThanOrEqualTo(Boolean value) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL >=", value, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualLessThan(Boolean value) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL <", value, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualLessThanOrEqualTo(Boolean value) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL <=", value, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualLike(Boolean value) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL like", value, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualNotLike(Boolean value) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL not like", value, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualIn(List<Boolean> values) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL in", values, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualNotIn(List<Boolean> values) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL not in", values, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualBetween(Boolean value1, Boolean value2) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL between", value1, value2, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleTiendaVirtualNotBetween(Boolean value1, Boolean value2) {
            addVisibleTiendaVirtualCriterion("VISIBLE_TIENDA_VIRTUAL not between", value1, value2, "visibleTiendaVirtual");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraIsNull() {
            addCriterion("VISIBLE_COMPRA is null");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraIsNotNull() {
            addCriterion("VISIBLE_COMPRA is not null");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraEqualTo(Boolean value) {
            addVisibleCompraCriterion("VISIBLE_COMPRA =", value, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraNotEqualTo(Boolean value) {
            addVisibleCompraCriterion("VISIBLE_COMPRA <>", value, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraGreaterThan(Boolean value) {
            addVisibleCompraCriterion("VISIBLE_COMPRA >", value, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraGreaterThanOrEqualTo(Boolean value) {
            addVisibleCompraCriterion("VISIBLE_COMPRA >=", value, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraLessThan(Boolean value) {
            addVisibleCompraCriterion("VISIBLE_COMPRA <", value, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraLessThanOrEqualTo(Boolean value) {
            addVisibleCompraCriterion("VISIBLE_COMPRA <=", value, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraLike(Boolean value) {
            addVisibleCompraCriterion("VISIBLE_COMPRA like", value, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraNotLike(Boolean value) {
            addVisibleCompraCriterion("VISIBLE_COMPRA not like", value, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraIn(List<Boolean> values) {
            addVisibleCompraCriterion("VISIBLE_COMPRA in", values, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraNotIn(List<Boolean> values) {
            addVisibleCompraCriterion("VISIBLE_COMPRA not in", values, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraBetween(Boolean value1, Boolean value2) {
            addVisibleCompraCriterion("VISIBLE_COMPRA between", value1, value2, "visibleCompra");
            return (Criteria) this;
        }

        public Criteria andVisibleCompraNotBetween(Boolean value1, Boolean value2) {
            addVisibleCompraCriterion("VISIBLE_COMPRA not between", value1, value2, "visibleCompra");
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

        public Criteria andManualIsNull() {
            addCriterion("MANUAL is null");
            return (Criteria) this;
        }

        public Criteria andManualIsNotNull() {
            addCriterion("MANUAL is not null");
            return (Criteria) this;
        }

        public Criteria andManualEqualTo(Boolean value) {
            addManualCriterion("MANUAL =", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualNotEqualTo(Boolean value) {
            addManualCriterion("MANUAL <>", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualGreaterThan(Boolean value) {
            addManualCriterion("MANUAL >", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualGreaterThanOrEqualTo(Boolean value) {
            addManualCriterion("MANUAL >=", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualLessThan(Boolean value) {
            addManualCriterion("MANUAL <", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualLessThanOrEqualTo(Boolean value) {
            addManualCriterion("MANUAL <=", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualLike(Boolean value) {
            addManualCriterion("MANUAL like", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualNotLike(Boolean value) {
            addManualCriterion("MANUAL not like", value, "manual");
            return (Criteria) this;
        }

        public Criteria andManualIn(List<Boolean> values) {
            addManualCriterion("MANUAL in", values, "manual");
            return (Criteria) this;
        }

        public Criteria andManualNotIn(List<Boolean> values) {
            addManualCriterion("MANUAL not in", values, "manual");
            return (Criteria) this;
        }

        public Criteria andManualBetween(Boolean value1, Boolean value2) {
            addManualCriterion("MANUAL between", value1, value2, "manual");
            return (Criteria) this;
        }

        public Criteria andManualNotBetween(Boolean value1, Boolean value2) {
            addManualCriterion("MANUAL not between", value1, value2, "manual");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaIsNull() {
            addCriterion("RECUENTO_AUTOMATICO_CAJA is null");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaIsNotNull() {
            addCriterion("RECUENTO_AUTOMATICO_CAJA is not null");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaEqualTo(Boolean value) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA =", value, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaNotEqualTo(Boolean value) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA <>", value, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaGreaterThan(Boolean value) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA >", value, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaGreaterThanOrEqualTo(Boolean value) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA >=", value, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaLessThan(Boolean value) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA <", value, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaLessThanOrEqualTo(Boolean value) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA <=", value, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaLike(Boolean value) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA like", value, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaNotLike(Boolean value) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA not like", value, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaIn(List<Boolean> values) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA in", values, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaNotIn(List<Boolean> values) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA not in", values, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaBetween(Boolean value1, Boolean value2) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA between", value1, value2, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andRecuentoAutomaticoCajaNotBetween(Boolean value1, Boolean value2) {
            addRecuentoAutomaticoCajaCriterion("RECUENTO_AUTOMATICO_CAJA not between", value1, value2, "recuentoAutomaticoCaja");
            return (Criteria) this;
        }

        public Criteria andUidActividadLikeInsensitive(String value) {
            addCriterion("upper(UID_ACTIVIDAD) like", value.toUpperCase(), "uidActividad");
            return (Criteria) this;
        }

        public Criteria andCodMedioPagoLikeInsensitive(String value) {
            addCriterion("upper(CODMEDPAG) like", value.toUpperCase(), "codMedioPago");
            return (Criteria) this;
        }

        public Criteria andDesMedioPagoLikeInsensitive(String value) {
            addCriterion("upper(DESMEDPAG) like", value.toUpperCase(), "desMedioPago");
            return (Criteria) this;
        }

        public Criteria andCodTipoEfectoLikeInsensitive(String value) {
            addCriterion("upper(CODTIPOEFEC) like", value.toUpperCase(), "codTipoEfecto");
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