
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the be.kzen.ergorr.model.ogc package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SortBy_QNAME = new QName("http://www.opengis.net/ogc", "SortBy");
    private final static QName _SpatialOps_QNAME = new QName("http://www.opengis.net/ogc", "spatialOps");
    private final static QName _GmlObjectId_QNAME = new QName("http://www.opengis.net/ogc", "GmlObjectId");
    private final static QName _Add_QNAME = new QName("http://www.opengis.net/ogc", "Add");
    private final static QName _PropertyName_QNAME = new QName("http://www.opengis.net/ogc", "PropertyName");
    private final static QName _Id_QNAME = new QName("http://www.opengis.net/ogc", "_Id");
    private final static QName _Sub_QNAME = new QName("http://www.opengis.net/ogc", "Sub");
    private final static QName _Mul_QNAME = new QName("http://www.opengis.net/ogc", "Mul");
    private final static QName _LogicOps_QNAME = new QName("http://www.opengis.net/ogc", "logicOps");
    private final static QName _Expression_QNAME = new QName("http://www.opengis.net/ogc", "expression");
    private final static QName _Filter_QNAME = new QName("http://www.opengis.net/ogc", "Filter");
    private final static QName _Function_QNAME = new QName("http://www.opengis.net/ogc", "Function");
    private final static QName _Div_QNAME = new QName("http://www.opengis.net/ogc", "Div");
    private final static QName _FeatureId_QNAME = new QName("http://www.opengis.net/ogc", "FeatureId");
    private final static QName _ComparisonOps_QNAME = new QName("http://www.opengis.net/ogc", "comparisonOps");
    private final static QName _Literal_QNAME = new QName("http://www.opengis.net/ogc", "Literal");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: be.kzen.ergorr.model.ogc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SortByType }
     * 
     */
    public SortByType createSortByType() {
        return new SortByType();
    }

    /**
     * Create an instance of {@link SpatialOperatorType }
     * 
     */
    public SpatialOperatorType createSpatialOperatorType() {
        return new SpatialOperatorType();
    }

    /**
     * Create an instance of {@link UpperBoundaryType }
     * 
     */
    public UpperBoundaryType createUpperBoundaryType() {
        return new UpperBoundaryType();
    }

    /**
     * Create an instance of {@link PropertyNameType }
     * 
     */
    public PropertyNameType createPropertyNameType() {
        return new PropertyNameType();
    }

    /**
     * Create an instance of {@link GmlObjectIdType }
     * 
     */
    public GmlObjectIdType createGmlObjectIdType() {
        return new GmlObjectIdType();
    }

    /**
     * Create an instance of {@link IdCapabilitiesType }
     * 
     */
    public IdCapabilitiesType createIdCapabilitiesType() {
        return new IdCapabilitiesType();
    }

    /**
     * Create an instance of {@link SimpleArithmetic }
     * 
     */
    public SimpleArithmetic createSimpleArithmetic() {
        return new SimpleArithmetic();
    }

    /**
     * Create an instance of {@link ScalarCapabilitiesType }
     * 
     */
    public ScalarCapabilitiesType createScalarCapabilitiesType() {
        return new ScalarCapabilitiesType();
    }

    /**
     * Create an instance of {@link FID }
     * 
     */
    public FID createFID() {
        return new FID();
    }

    /**
     * Create an instance of {@link SortPropertyType }
     * 
     */
    public SortPropertyType createSortPropertyType() {
        return new SortPropertyType();
    }

    /**
     * Create an instance of {@link LowerBoundaryType }
     * 
     */
    public LowerBoundaryType createLowerBoundaryType() {
        return new LowerBoundaryType();
    }

    /**
     * Create an instance of {@link SpatialOperatorsType }
     * 
     */
    public SpatialOperatorsType createSpatialOperatorsType() {
        return new SpatialOperatorsType();
    }

    /**
     * Create an instance of {@link FunctionType }
     * 
     */
    public FunctionType createFunctionType() {
        return new FunctionType();
    }

    /**
     * Create an instance of {@link BinarySpatialOpType }
     * 
     */
    public BinarySpatialOpType createBinarySpatialOpType() {
        return new BinarySpatialOpType();
    }

    /**
     * Create an instance of {@link UnaryLogicOpType }
     * 
     */
    public UnaryLogicOpType createUnaryLogicOpType() {
        return new UnaryLogicOpType();
    }

    /**
     * Create an instance of {@link EID }
     * 
     */
    public EID createEID() {
        return new EID();
    }

    /**
     * Create an instance of {@link PropertyIsLikeType }
     * 
     */
    public PropertyIsLikeType createPropertyIsLikeType() {
        return new PropertyIsLikeType();
    }

    /**
     * Create an instance of {@link ComparisonOperatorsType }
     * 
     */
    public ComparisonOperatorsType createComparisonOperatorsType() {
        return new ComparisonOperatorsType();
    }

    /**
     * Create an instance of {@link FunctionNamesType }
     * 
     */
    public FunctionNamesType createFunctionNamesType() {
        return new FunctionNamesType();
    }

    /**
     * Create an instance of {@link FunctionsType }
     * 
     */
    public FunctionsType createFunctionsType() {
        return new FunctionsType();
    }

    /**
     * Create an instance of {@link DistanceType }
     * 
     */
    public DistanceType createDistanceType() {
        return new DistanceType();
    }

    /**
     * Create an instance of {@link FilterCapabilities }
     * 
     */
    public FilterCapabilities createFilterCapabilities() {
        return new FilterCapabilities();
    }

    /**
     * Create an instance of {@link BinaryOperatorType }
     * 
     */
    public BinaryOperatorType createBinaryOperatorType() {
        return new BinaryOperatorType();
    }

    /**
     * Create an instance of {@link ArithmeticOperatorsType }
     * 
     */
    public ArithmeticOperatorsType createArithmeticOperatorsType() {
        return new ArithmeticOperatorsType();
    }

    /**
     * Create an instance of {@link BBOXType }
     * 
     */
    public BBOXType createBBOXType() {
        return new BBOXType();
    }

    /**
     * Create an instance of {@link DistanceBufferType }
     * 
     */
    public DistanceBufferType createDistanceBufferType() {
        return new DistanceBufferType();
    }

    /**
     * Create an instance of {@link FeatureIdType }
     * 
     */
    public FeatureIdType createFeatureIdType() {
        return new FeatureIdType();
    }

    /**
     * Create an instance of {@link PropertyIsNullType }
     * 
     */
    public PropertyIsNullType createPropertyIsNullType() {
        return new PropertyIsNullType();
    }

    /**
     * Create an instance of {@link SpatialCapabilitiesType }
     * 
     */
    public SpatialCapabilitiesType createSpatialCapabilitiesType() {
        return new SpatialCapabilitiesType();
    }

    /**
     * Create an instance of {@link BinaryLogicOpType }
     * 
     */
    public BinaryLogicOpType createBinaryLogicOpType() {
        return new BinaryLogicOpType();
    }

    /**
     * Create an instance of {@link GeometryOperandsType }
     * 
     */
    public GeometryOperandsType createGeometryOperandsType() {
        return new GeometryOperandsType();
    }

    /**
     * Create an instance of {@link FilterType }
     * 
     */
    public FilterType createFilterType() {
        return new FilterType();
    }

    /**
     * Create an instance of {@link FunctionNameType }
     * 
     */
    public FunctionNameType createFunctionNameType() {
        return new FunctionNameType();
    }

    /**
     * Create an instance of {@link LiteralType }
     * 
     */
    public LiteralType createLiteralType() {
        return new LiteralType();
    }

    /**
     * Create an instance of {@link BinaryComparisonOpType }
     * 
     */
    public BinaryComparisonOpType createBinaryComparisonOpType() {
        return new BinaryComparisonOpType();
    }

    /**
     * Create an instance of {@link LogicalOperators }
     * 
     */
    public LogicalOperators createLogicalOperators() {
        return new LogicalOperators();
    }

    /**
     * Create an instance of {@link PropertyIsBetweenType }
     * 
     */
    public PropertyIsBetweenType createPropertyIsBetweenType() {
        return new PropertyIsBetweenType();
    }

    /**
     * Create an instance of {@link Or }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Or", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "logicOps")
    public Or createOr(BinaryLogicOpType value) {
        return new Or(value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SortByType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "SortBy")
    public JAXBElement<SortByType> createSortBy(SortByType value) {
        return new JAXBElement<SortByType>(_SortBy_QNAME, SortByType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SpatialOpsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "spatialOps")
    public JAXBElement<SpatialOpsType> createSpatialOps(SpatialOpsType value) {
        return new JAXBElement<SpatialOpsType>(_SpatialOps_QNAME, SpatialOpsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GmlObjectIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "GmlObjectId", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "_Id")
    public JAXBElement<GmlObjectIdType> createGmlObjectId(GmlObjectIdType value) {
        return new JAXBElement<GmlObjectIdType>(_GmlObjectId_QNAME, GmlObjectIdType.class, null, value);
    }

    /**
     * Create an instance of {@link PropertyIsLessThanOrEqualTo }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "PropertyIsLessThanOrEqualTo", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "comparisonOps")
    public PropertyIsLessThanOrEqualTo createPropertyIsLessThanOrEqualTo(BinaryComparisonOpType value) {
        return new PropertyIsLessThanOrEqualTo(value);
    }

    /**
     * Create an instance of {@link PropertyIsNotEqualTo }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "PropertyIsNotEqualTo", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "comparisonOps")
    public PropertyIsNotEqualTo createPropertyIsNotEqualTo(BinaryComparisonOpType value) {
        return new PropertyIsNotEqualTo(value);
    }

    /**
     * Create an instance of {@link And }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "And", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "logicOps")
    public And createAnd(BinaryLogicOpType value) {
        return new And(value);
    }

    /**
     * Create an instance of {@link Beyond }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Beyond", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public Beyond createBeyond(DistanceBufferType value) {
        return new Beyond(value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BinaryOperatorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Add", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "expression")
    public JAXBElement<BinaryOperatorType> createAdd(BinaryOperatorType value) {
        return new JAXBElement<BinaryOperatorType>(_Add_QNAME, BinaryOperatorType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PropertyNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "PropertyName", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "expression")
    public JAXBElement<PropertyNameType> createPropertyName(PropertyNameType value) {
        return new JAXBElement<PropertyNameType>(_PropertyName_QNAME, PropertyNameType.class, null, value);
    }

    /**
     * Create an instance of {@link DWithin }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "DWithin", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public DWithin createDWithin(DistanceBufferType value) {
        return new DWithin(value);
    }

    /**
     * Create an instance of {@link Disjoint }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Disjoint", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public Disjoint createDisjoint(BinarySpatialOpType value) {
        return new Disjoint(value);
    }

    /**
     * Create an instance of {@link BBOX }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "BBOX", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public BBOX createBBOX(BBOXType value) {
        return new BBOX(value);
    }

    /**
     * Create an instance of {@link Touches }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Touches", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public Touches createTouches(BinarySpatialOpType value) {
        return new Touches(value);
    }

    /**
     * Create an instance of {@link Contains }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Contains", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public Contains createContains(BinarySpatialOpType value) {
        return new Contains(value);
    }

    /**
     * Create an instance of {@link Intersects }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Intersects", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public Intersects createIntersects(BinarySpatialOpType value) {
        return new Intersects(value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "_Id")
    public JAXBElement<AbstractIdType> createId(AbstractIdType value) {
        return new JAXBElement<AbstractIdType>(_Id_QNAME, AbstractIdType.class, null, value);
    }

    /**
     * Create an instance of {@link Crosses }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Crosses", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public Crosses createCrosses(BinarySpatialOpType value) {
        return new Crosses(value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BinaryOperatorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Sub", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "expression")
    public JAXBElement<BinaryOperatorType> createSub(BinaryOperatorType value) {
        return new JAXBElement<BinaryOperatorType>(_Sub_QNAME, BinaryOperatorType.class, null, value);
    }

    /**
     * Create an instance of {@link PropertyIsGreaterThan }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "PropertyIsGreaterThan", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "comparisonOps")
    public PropertyIsGreaterThan createPropertyIsGreaterThan(BinaryComparisonOpType value) {
        return new PropertyIsGreaterThan(value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BinaryOperatorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Mul", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "expression")
    public JAXBElement<BinaryOperatorType> createMul(BinaryOperatorType value) {
        return new JAXBElement<BinaryOperatorType>(_Mul_QNAME, BinaryOperatorType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogicOpsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "logicOps")
    public JAXBElement<LogicOpsType> createLogicOps(LogicOpsType value) {
        return new JAXBElement<LogicOpsType>(_LogicOps_QNAME, LogicOpsType.class, null, value);
    }

    /**
     * Create an instance of {@link PropertyIsLessThan }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "PropertyIsLessThan", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "comparisonOps")
    public PropertyIsLessThan createPropertyIsLessThan(BinaryComparisonOpType value) {
        return new PropertyIsLessThan(value);
    }

    /**
     * Create an instance of {@link PropertyIsEqualTo }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "PropertyIsEqualTo", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "comparisonOps")
    public PropertyIsEqualTo createPropertyIsEqualTo(BinaryComparisonOpType value) {
        return new PropertyIsEqualTo(value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExpressionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "expression")
    public JAXBElement<ExpressionType> createExpression(ExpressionType value) {
        return new JAXBElement<ExpressionType>(_Expression_QNAME, ExpressionType.class, null, value);
    }

    /**
     * Create an instance of {@link Not }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Not", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "logicOps")
    public Not createNot(UnaryLogicOpType value) {
        return new Not(value);
    }

    /**
     * Create an instance of {@link PropertyIsNull }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "PropertyIsNull", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "comparisonOps")
    public PropertyIsNull createPropertyIsNull(PropertyIsNullType value) {
        return new PropertyIsNull(value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FilterType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Filter")
    public JAXBElement<FilterType> createFilter(FilterType value) {
        return new JAXBElement<FilterType>(_Filter_QNAME, FilterType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FunctionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Function", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "expression")
    public JAXBElement<FunctionType> createFunction(FunctionType value) {
        return new JAXBElement<FunctionType>(_Function_QNAME, FunctionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BinaryOperatorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Div", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "expression")
    public JAXBElement<BinaryOperatorType> createDiv(BinaryOperatorType value) {
        return new JAXBElement<BinaryOperatorType>(_Div_QNAME, BinaryOperatorType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FeatureIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "FeatureId", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "_Id")
    public JAXBElement<FeatureIdType> createFeatureId(FeatureIdType value) {
        return new JAXBElement<FeatureIdType>(_FeatureId_QNAME, FeatureIdType.class, null, value);
    }

    /**
     * Create an instance of {@link PropertyIsBetween }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "PropertyIsBetween", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "comparisonOps")
    public PropertyIsBetween createPropertyIsBetween(PropertyIsBetweenType value) {
        return new PropertyIsBetween(value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComparisonOpsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "comparisonOps")
    public JAXBElement<ComparisonOpsType> createComparisonOps(ComparisonOpsType value) {
        return new JAXBElement<ComparisonOpsType>(_ComparisonOps_QNAME, ComparisonOpsType.class, null, value);
    }

    /**
     * Create an instance of {@link Overlaps }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Overlaps", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public Overlaps createOverlaps(BinarySpatialOpType value) {
        return new Overlaps(value);
    }

    /**
     * Create an instance of {@link PropertyIsGreaterThanOrEqualTo }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "PropertyIsGreaterThanOrEqualTo", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "comparisonOps")
    public PropertyIsGreaterThanOrEqualTo createPropertyIsGreaterThanOrEqualTo(BinaryComparisonOpType value) {
        return new PropertyIsGreaterThanOrEqualTo(value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LiteralType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Literal", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "expression")
    public JAXBElement<LiteralType> createLiteral(LiteralType value) {
        return new JAXBElement<LiteralType>(_Literal_QNAME, LiteralType.class, null, value);
    }

    /**
     * Create an instance of {@link Within }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Within", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public Within createWithin(BinarySpatialOpType value) {
        return new Within(value);
    }

    /**
     * Create an instance of {@link Equals }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "Equals", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "spatialOps")
    public Equals createEquals(BinarySpatialOpType value) {
        return new Equals(value);
    }

    /**
     * Create an instance of {@link PropertyIsLike }}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ogc", name = "PropertyIsLike", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "comparisonOps")
    public PropertyIsLike createPropertyIsLike(PropertyIsLikeType value) {
        return new PropertyIsLike(value);
    }

}
