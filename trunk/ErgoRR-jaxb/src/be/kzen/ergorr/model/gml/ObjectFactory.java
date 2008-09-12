
package be.kzen.ergorr.model.gml;

import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the be.kzen.ergorr.model.gml package. 
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

    private final static QName _CircleByCenterPoint_QNAME = new QName("http://www.opengis.net/gml/3.2", "CircleByCenterPoint");
    private final static QName _Sphere_QNAME = new QName("http://www.opengis.net/gml/3.2", "Sphere");
    private final static QName _AbstractParametricCurveSurface_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractParametricCurveSurface");
    private final static QName _AbstractSurfacePatch_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractSurfacePatch");
    private final static QName _DirectedObservation_QNAME = new QName("http://www.opengis.net/gml/3.2", "DirectedObservation");
    private final static QName _QuantityTypeReference_QNAME = new QName("http://www.opengis.net/gml/3.2", "quantityTypeReference");
    private final static QName _CompositeCurve_QNAME = new QName("http://www.opengis.net/gml/3.2", "CompositeCurve");
    private final static QName _BoundedBy_QNAME = new QName("http://www.opengis.net/gml/3.2", "boundedBy");
    private final static QName _ArcString_QNAME = new QName("http://www.opengis.net/gml/3.2", "ArcString");
    private final static QName _TopoPoint_QNAME = new QName("http://www.opengis.net/gml/3.2", "TopoPoint");
    private final static QName _MultiExtentOf_QNAME = new QName("http://www.opengis.net/gml/3.2", "multiExtentOf");
    private final static QName _DirectedNode_QNAME = new QName("http://www.opengis.net/gml/3.2", "directedNode");
    private final static QName _AbstractScalarValueList_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractScalarValueList");
    private final static QName _TopoPrimitiveMember_QNAME = new QName("http://www.opengis.net/gml/3.2", "topoPrimitiveMember");
    private final static QName _MultiPoint_QNAME = new QName("http://www.opengis.net/gml/3.2", "MultiPoint");
    private final static QName _AbstractReference_QNAME = new QName("http://www.opengis.net/gml/3.2", "abstractReference");
    private final static QName _MultiSurfaceCoverage_QNAME = new QName("http://www.opengis.net/gml/3.2", "MultiSurfaceCoverage");
    private final static QName _TargetElement_QNAME = new QName("http://www.opengis.net/gml/3.2", "targetElement");
    private final static QName _Vector_QNAME = new QName("http://www.opengis.net/gml/3.2", "vector");
    private final static QName _Count_QNAME = new QName("http://www.opengis.net/gml/3.2", "Count");
    private final static QName _Direction_QNAME = new QName("http://www.opengis.net/gml/3.2", "direction");
    private final static QName _Target_QNAME = new QName("http://www.opengis.net/gml/3.2", "target");
    private final static QName _EnvelopeWithTimePeriod_QNAME = new QName("http://www.opengis.net/gml/3.2", "EnvelopeWithTimePeriod");
    private final static QName _ArcByBulge_QNAME = new QName("http://www.opengis.net/gml/3.2", "ArcByBulge");
    private final static QName _TopoVolume_QNAME = new QName("http://www.opengis.net/gml/3.2", "TopoVolume");
    private final static QName _MappingRule_QNAME = new QName("http://www.opengis.net/gml/3.2", "MappingRule");
    private final static QName _DataSourceReference_QNAME = new QName("http://www.opengis.net/gml/3.2", "dataSourceReference");
    private final static QName _PointMembers_QNAME = new QName("http://www.opengis.net/gml/3.2", "pointMembers");
    private final static QName _Quantity_QNAME = new QName("http://www.opengis.net/gml/3.2", "Quantity");
    private final static QName _DataBlock_QNAME = new QName("http://www.opengis.net/gml/3.2", "DataBlock");
    private final static QName _File_QNAME = new QName("http://www.opengis.net/gml/3.2", "File");
    private final static QName _LocationReference_QNAME = new QName("http://www.opengis.net/gml/3.2", "locationReference");
    private final static QName _TopoSurface_QNAME = new QName("http://www.opengis.net/gml/3.2", "TopoSurface");
    private final static QName _Cone_QNAME = new QName("http://www.opengis.net/gml/3.2", "Cone");
    private final static QName _CompositeValue_QNAME = new QName("http://www.opengis.net/gml/3.2", "CompositeValue");
    private final static QName _TimeReferenceSystem_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeReferenceSystem");
    private final static QName _AbstractMetaData_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractMetaData");
    private final static QName _TimePosition_QNAME = new QName("http://www.opengis.net/gml/3.2", "timePosition");
    private final static QName _AbstractRing_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractRing");
    private final static QName _DirectedTopoSolid_QNAME = new QName("http://www.opengis.net/gml/3.2", "directedTopoSolid");
    private final static QName _ArcStringByBulge_QNAME = new QName("http://www.opengis.net/gml/3.2", "ArcStringByBulge");
    private final static QName _TimeEdge_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeEdge");
    private final static QName _MultiPointCoverage_QNAME = new QName("http://www.opengis.net/gml/3.2", "MultiPointCoverage");
    private final static QName _DynamicFeature_QNAME = new QName("http://www.opengis.net/gml/3.2", "DynamicFeature");
    private final static QName _Dictionary_QNAME = new QName("http://www.opengis.net/gml/3.2", "Dictionary");
    private final static QName _DescriptionReference_QNAME = new QName("http://www.opengis.net/gml/3.2", "descriptionReference");
    private final static QName _BaseCurve_QNAME = new QName("http://www.opengis.net/gml/3.2", "baseCurve");
    private final static QName _PointMember_QNAME = new QName("http://www.opengis.net/gml/3.2", "pointMember");
    private final static QName _ValueArray_QNAME = new QName("http://www.opengis.net/gml/3.2", "ValueArray");
    private final static QName _AbstractSurface_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractSurface");
    private final static QName _BaseSurface_QNAME = new QName("http://www.opengis.net/gml/3.2", "baseSurface");
    private final static QName _TimeOrdinalReferenceSystem_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeOrdinalReferenceSystem");
    private final static QName _LineString_QNAME = new QName("http://www.opengis.net/gml/3.2", "LineString");
    private final static QName _Observation_QNAME = new QName("http://www.opengis.net/gml/3.2", "Observation");
    private final static QName _TopoSurfaceProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "topoSurfaceProperty");
    private final static QName _BaseUnit_QNAME = new QName("http://www.opengis.net/gml/3.2", "BaseUnit");
    private final static QName _CompositeSolid_QNAME = new QName("http://www.opengis.net/gml/3.2", "CompositeSolid");
    private final static QName _Envelope_QNAME = new QName("http://www.opengis.net/gml/3.2", "Envelope");
    private final static QName _UnitOfMeasure_QNAME = new QName("http://www.opengis.net/gml/3.2", "unitOfMeasure");
    private final static QName _MaximalComplex_QNAME = new QName("http://www.opengis.net/gml/3.2", "maximalComplex");
    private final static QName _LinearRing_QNAME = new QName("http://www.opengis.net/gml/3.2", "LinearRing");
    private final static QName _AbstractGML_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractGML");
    private final static QName _DynamicFeatureCollection_QNAME = new QName("http://www.opengis.net/gml/3.2", "DynamicFeatureCollection");
    private final static QName _Identifier_QNAME = new QName("http://www.opengis.net/gml/3.2", "identifier");
    private final static QName _BSpline_QNAME = new QName("http://www.opengis.net/gml/3.2", "BSpline");
    private final static QName _MultiGeometry_QNAME = new QName("http://www.opengis.net/gml/3.2", "MultiGeometry");
    private final static QName _TopoComplex_QNAME = new QName("http://www.opengis.net/gml/3.2", "TopoComplex");
    private final static QName _CoverageMappingRule_QNAME = new QName("http://www.opengis.net/gml/3.2", "CoverageMappingRule");
    private final static QName _OrientableCurve_QNAME = new QName("http://www.opengis.net/gml/3.2", "OrientableCurve");
    private final static QName _SurfaceMember_QNAME = new QName("http://www.opengis.net/gml/3.2", "surfaceMember");
    private final static QName _Solid_QNAME = new QName("http://www.opengis.net/gml/3.2", "Solid");
    private final static QName _MultiCurveCoverage_QNAME = new QName("http://www.opengis.net/gml/3.2", "MultiCurveCoverage");
    private final static QName _AssociationName_QNAME = new QName("http://www.opengis.net/gml/3.2", "associationName");
    private final static QName _Category_QNAME = new QName("http://www.opengis.net/gml/3.2", "Category");
    private final static QName _MultiSolid_QNAME = new QName("http://www.opengis.net/gml/3.2", "MultiSolid");
    private final static QName _AbstractTimePrimitive_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractTimePrimitive");
    private final static QName _TimeTopologyComplex_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeTopologyComplex");
    private final static QName _ArcByCenterPoint_QNAME = new QName("http://www.opengis.net/gml/3.2", "ArcByCenterPoint");
    private final static QName _PosList_QNAME = new QName("http://www.opengis.net/gml/3.2", "posList");
    private final static QName _MetaDataProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "metaDataProperty");
    private final static QName _TimeInterval_QNAME = new QName("http://www.opengis.net/gml/3.2", "timeInterval");
    private final static QName _CategoryList_QNAME = new QName("http://www.opengis.net/gml/3.2", "CategoryList");
    private final static QName _DoubleOrNilReasonTupleList_QNAME = new QName("http://www.opengis.net/gml/3.2", "doubleOrNilReasonTupleList");
    private final static QName _AbstractTimeTopologyPrimitive_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractTimeTopologyPrimitive");
    private final static QName _CurveMembers_QNAME = new QName("http://www.opengis.net/gml/3.2", "curveMembers");
    private final static QName _LocationName_QNAME = new QName("http://www.opengis.net/gml/3.2", "locationName");
    private final static QName _Pos_QNAME = new QName("http://www.opengis.net/gml/3.2", "pos");
    private final static QName _AbstractObject_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractObject");
    private final static QName _ValueProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "valueProperty");
    private final static QName _AbstractGriddedSurface_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractGriddedSurface");
    private final static QName _Exterior_QNAME = new QName("http://www.opengis.net/gml/3.2", "exterior");
    private final static QName _AbstractGeometricPrimitive_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractGeometricPrimitive");
    private final static QName _DefaultCodeSpace_QNAME = new QName("http://www.opengis.net/gml/3.2", "defaultCodeSpace");
    private final static QName _AbstractTopology_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractTopology");
    private final static QName _Null_QNAME = new QName("http://www.opengis.net/gml/3.2", "Null");
    private final static QName _TimeCoordinateSystem_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeCoordinateSystem");
    private final static QName _GeometryMember_QNAME = new QName("http://www.opengis.net/gml/3.2", "geometryMember");
    private final static QName _TopoCurve_QNAME = new QName("http://www.opengis.net/gml/3.2", "TopoCurve");
    private final static QName _TimeNode_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeNode");
    private final static QName _Curve_QNAME = new QName("http://www.opengis.net/gml/3.2", "Curve");
    private final static QName _TimePeriod_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimePeriod");
    private final static QName _AbstractCurve_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractCurve");
    private final static QName _AbstractValue_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractValue");
    private final static QName _Clothoid_QNAME = new QName("http://www.opengis.net/gml/3.2", "Clothoid");
    private final static QName _ReversePropertyName_QNAME = new QName("http://www.opengis.net/gml/3.2", "reversePropertyName");
    private final static QName _ResultOf_QNAME = new QName("http://www.opengis.net/gml/3.2", "resultOf");
    private final static QName _AbstractTimeSlice_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractTimeSlice");
    private final static QName _CubicSpline_QNAME = new QName("http://www.opengis.net/gml/3.2", "CubicSpline");
    private final static QName _AbstractDiscreteCoverage_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractDiscreteCoverage");
    private final static QName _Tin_QNAME = new QName("http://www.opengis.net/gml/3.2", "Tin");
    private final static QName _Face_QNAME = new QName("http://www.opengis.net/gml/3.2", "Face");
    private final static QName _TopoCurveProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "topoCurveProperty");
    private final static QName _TimeOrdinalEra_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeOrdinalEra");
    private final static QName _DomainSet_QNAME = new QName("http://www.opengis.net/gml/3.2", "domainSet");
    private final static QName _MultiSurface_QNAME = new QName("http://www.opengis.net/gml/3.2", "MultiSurface");
    private final static QName _Angle_QNAME = new QName("http://www.opengis.net/gml/3.2", "angle");
    private final static QName _TopoPrimitiveMembers_QNAME = new QName("http://www.opengis.net/gml/3.2", "topoPrimitiveMembers");
    private final static QName _CurveMember_QNAME = new QName("http://www.opengis.net/gml/3.2", "curveMember");
    private final static QName _TimeCalendar_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeCalendar");
    private final static QName _RectifiedGrid_QNAME = new QName("http://www.opengis.net/gml/3.2", "RectifiedGrid");
    private final static QName _AbstractTimeObject_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractTimeObject");
    private final static QName _RectifiedGridCoverage_QNAME = new QName("http://www.opengis.net/gml/3.2", "RectifiedGridCoverage");
    private final static QName _Interior_QNAME = new QName("http://www.opengis.net/gml/3.2", "interior");
    private final static QName _Location_QNAME = new QName("http://www.opengis.net/gml/3.2", "location");
    private final static QName _SubComplex_QNAME = new QName("http://www.opengis.net/gml/3.2", "subComplex");
    private final static QName _TopoPointProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "topoPointProperty");
    private final static QName _TupleList_QNAME = new QName("http://www.opengis.net/gml/3.2", "tupleList");
    private final static QName _AbstractScalarValue_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractScalarValue");
    private final static QName _MultiCurve_QNAME = new QName("http://www.opengis.net/gml/3.2", "MultiCurve");
    private final static QName _TimeCalendarEra_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeCalendarEra");
    private final static QName _OffsetCurve_QNAME = new QName("http://www.opengis.net/gml/3.2", "OffsetCurve");
    private final static QName _ConversionToPreferredUnit_QNAME = new QName("http://www.opengis.net/gml/3.2", "conversionToPreferredUnit");
    private final static QName _AbstractContinuousCoverage_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractContinuousCoverage");
    private final static QName _GridFunction_QNAME = new QName("http://www.opengis.net/gml/3.2", "GridFunction");
    private final static QName _Duration_QNAME = new QName("http://www.opengis.net/gml/3.2", "duration");
    private final static QName _CenterOf_QNAME = new QName("http://www.opengis.net/gml/3.2", "centerOf");
    private final static QName _DirectedFace_QNAME = new QName("http://www.opengis.net/gml/3.2", "directedFace");
    private final static QName _GmlProfileSchema_QNAME = new QName("http://www.opengis.net/gml/3.2", "gmlProfileSchema");
    private final static QName _Triangle_QNAME = new QName("http://www.opengis.net/gml/3.2", "Triangle");
    private final static QName _TopoSolid_QNAME = new QName("http://www.opengis.net/gml/3.2", "TopoSolid");
    private final static QName _AbstractAssociationRole_QNAME = new QName("http://www.opengis.net/gml/3.2", "abstractAssociationRole");
    private final static QName _AbstractFeature_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractFeature");
    private final static QName _TimeClock_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeClock");
    private final static QName _AbstractTopoPrimitive_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractTopoPrimitive");
    private final static QName _Bezier_QNAME = new QName("http://www.opengis.net/gml/3.2", "Bezier");
    private final static QName _DirectedObservationAtDistance_QNAME = new QName("http://www.opengis.net/gml/3.2", "DirectedObservationAtDistance");
    private final static QName _Patches_QNAME = new QName("http://www.opengis.net/gml/3.2", "patches");
    private final static QName _GeometryMembers_QNAME = new QName("http://www.opengis.net/gml/3.2", "geometryMembers");
    private final static QName _DictionaryEntry_QNAME = new QName("http://www.opengis.net/gml/3.2", "dictionaryEntry");
    private final static QName _LocationKeyWord_QNAME = new QName("http://www.opengis.net/gml/3.2", "LocationKeyWord");
    private final static QName _AbstractGeometry_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractGeometry");
    private final static QName _TimeInstant_QNAME = new QName("http://www.opengis.net/gml/3.2", "TimeInstant");
    private final static QName _Grid_QNAME = new QName("http://www.opengis.net/gml/3.2", "Grid");
    private final static QName _AbstractTimeGeometricPrimitive_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractTimeGeometricPrimitive");
    private final static QName _Cylinder_QNAME = new QName("http://www.opengis.net/gml/3.2", "Cylinder");
    private final static QName _Segments_QNAME = new QName("http://www.opengis.net/gml/3.2", "segments");
    private final static QName _Subject_QNAME = new QName("http://www.opengis.net/gml/3.2", "subject");
    private final static QName _GeodesicString_QNAME = new QName("http://www.opengis.net/gml/3.2", "GeodesicString");
    private final static QName _Definition_QNAME = new QName("http://www.opengis.net/gml/3.2", "Definition");
    private final static QName _ValueComponents_QNAME = new QName("http://www.opengis.net/gml/3.2", "valueComponents");
    private final static QName _LocationString_QNAME = new QName("http://www.opengis.net/gml/3.2", "LocationString");
    private final static QName _AbstractGeometricAggregate_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractGeometricAggregate");
    private final static QName _SolidMembers_QNAME = new QName("http://www.opengis.net/gml/3.2", "solidMembers");
    private final static QName _Circle_QNAME = new QName("http://www.opengis.net/gml/3.2", "Circle");
    private final static QName _CurveProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "curveProperty");
    private final static QName _ConventionalUnit_QNAME = new QName("http://www.opengis.net/gml/3.2", "ConventionalUnit");
    private final static QName _SurfaceMembers_QNAME = new QName("http://www.opengis.net/gml/3.2", "surfaceMembers");
    private final static QName _GeometricComplex_QNAME = new QName("http://www.opengis.net/gml/3.2", "GeometricComplex");
    private final static QName _TriangulatedSurface_QNAME = new QName("http://www.opengis.net/gml/3.2", "TriangulatedSurface");
    private final static QName _QuantityExtent_QNAME = new QName("http://www.opengis.net/gml/3.2", "QuantityExtent");
    private final static QName _LineStringSegment_QNAME = new QName("http://www.opengis.net/gml/3.2", "LineStringSegment");
    private final static QName _CompositeSurface_QNAME = new QName("http://www.opengis.net/gml/3.2", "CompositeSurface");
    private final static QName _SolidProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "solidProperty");
    private final static QName _GridCoverage_QNAME = new QName("http://www.opengis.net/gml/3.2", "GridCoverage");
    private final static QName _ValidTime_QNAME = new QName("http://www.opengis.net/gml/3.2", "validTime");
    private final static QName _UnitDefinition_QNAME = new QName("http://www.opengis.net/gml/3.2", "UnitDefinition");
    private final static QName _SuperComplex_QNAME = new QName("http://www.opengis.net/gml/3.2", "superComplex");
    private final static QName _CoverageFunction_QNAME = new QName("http://www.opengis.net/gml/3.2", "coverageFunction");
    private final static QName _MultiSolidCoverage_QNAME = new QName("http://www.opengis.net/gml/3.2", "MultiSolidCoverage");
    private final static QName _RangeSet_QNAME = new QName("http://www.opengis.net/gml/3.2", "rangeSet");
    private final static QName _PolyhedralSurface_QNAME = new QName("http://www.opengis.net/gml/3.2", "PolyhedralSurface");
    private final static QName _Name_QNAME = new QName("http://www.opengis.net/gml/3.2", "name");
    private final static QName _Remarks_QNAME = new QName("http://www.opengis.net/gml/3.2", "remarks");
    private final static QName _CategoryExtent_QNAME = new QName("http://www.opengis.net/gml/3.2", "CategoryExtent");
    private final static QName _RangeParameters_QNAME = new QName("http://www.opengis.net/gml/3.2", "rangeParameters");
    private final static QName _Measure_QNAME = new QName("http://www.opengis.net/gml/3.2", "measure");
    private final static QName _QuantityList_QNAME = new QName("http://www.opengis.net/gml/3.2", "QuantityList");
    private final static QName _CountList_QNAME = new QName("http://www.opengis.net/gml/3.2", "CountList");
    private final static QName _PolygonPatch_QNAME = new QName("http://www.opengis.net/gml/3.2", "PolygonPatch");
    private final static QName _Boolean_QNAME = new QName("http://www.opengis.net/gml/3.2", "Boolean");
    private final static QName _AbstractSolid_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractSolid");
    private final static QName _AffinePlacement_QNAME = new QName("http://www.opengis.net/gml/3.2", "AffinePlacement");
    private final static QName _SurfaceProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "surfaceProperty");
    private final static QName _AbstractCurveSegment_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractCurveSegment");
    private final static QName _AbstractImplicitGeometry_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractImplicitGeometry");
    private final static QName _Surface_QNAME = new QName("http://www.opengis.net/gml/3.2", "Surface");
    private final static QName _AbstractStrictAssociationRole_QNAME = new QName("http://www.opengis.net/gml/3.2", "abstractStrictAssociationRole");
    private final static QName _Shell_QNAME = new QName("http://www.opengis.net/gml/3.2", "Shell");
    private final static QName _AbstractTimeComplex_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractTimeComplex");
    private final static QName _Point_QNAME = new QName("http://www.opengis.net/gml/3.2", "Point");
    private final static QName _AbstractCoverage_QNAME = new QName("http://www.opengis.net/gml/3.2", "AbstractCoverage");
    private final static QName _Using_QNAME = new QName("http://www.opengis.net/gml/3.2", "using");
    private final static QName _Arc_QNAME = new QName("http://www.opengis.net/gml/3.2", "Arc");
    private final static QName _DynamicMembers_QNAME = new QName("http://www.opengis.net/gml/3.2", "dynamicMembers");
    private final static QName _PointProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "pointProperty");
    private final static QName _Node_QNAME = new QName("http://www.opengis.net/gml/3.2", "Node");
    private final static QName _AbstractInlineProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "abstractInlineProperty");
    private final static QName _Edge_QNAME = new QName("http://www.opengis.net/gml/3.2", "Edge");
    private final static QName _ValueComponent_QNAME = new QName("http://www.opengis.net/gml/3.2", "valueComponent");
    private final static QName _RoughConversionToPreferredUnit_QNAME = new QName("http://www.opengis.net/gml/3.2", "roughConversionToPreferredUnit");
    private final static QName _CatalogSymbol_QNAME = new QName("http://www.opengis.net/gml/3.2", "catalogSymbol");
    private final static QName _DerivedUnit_QNAME = new QName("http://www.opengis.net/gml/3.2", "DerivedUnit");
    private final static QName _ExtentOf_QNAME = new QName("http://www.opengis.net/gml/3.2", "extentOf");
    private final static QName _CountExtent_QNAME = new QName("http://www.opengis.net/gml/3.2", "CountExtent");
    private final static QName _DirectedEdge_QNAME = new QName("http://www.opengis.net/gml/3.2", "directedEdge");
    private final static QName _Ring_QNAME = new QName("http://www.opengis.net/gml/3.2", "Ring");
    private final static QName _TopoVolumeProperty_QNAME = new QName("http://www.opengis.net/gml/3.2", "topoVolumeProperty");
    private final static QName _Rectangle_QNAME = new QName("http://www.opengis.net/gml/3.2", "Rectangle");
    private final static QName _DerivationUnitTerm_QNAME = new QName("http://www.opengis.net/gml/3.2", "derivationUnitTerm");
    private final static QName _History_QNAME = new QName("http://www.opengis.net/gml/3.2", "history");
    private final static QName _Polygon_QNAME = new QName("http://www.opengis.net/gml/3.2", "Polygon");
    private final static QName _Geodesic_QNAME = new QName("http://www.opengis.net/gml/3.2", "Geodesic");
    private final static QName _SolidMember_QNAME = new QName("http://www.opengis.net/gml/3.2", "solidMember");
    private final static QName _OrientableSurface_QNAME = new QName("http://www.opengis.net/gml/3.2", "OrientableSurface");
    private final static QName _BooleanList_QNAME = new QName("http://www.opengis.net/gml/3.2", "BooleanList");
    private final static QName _GridTypeLimits_QNAME = new QName("http://www.opengis.net/gml/3.2", "limits");
    private final static QName _GridTypeAxisName_QNAME = new QName("http://www.opengis.net/gml/3.2", "axisName");
    private final static QName _GridTypeAxisLabels_QNAME = new QName("http://www.opengis.net/gml/3.2", "axisLabels");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: be.kzen.ergorr.model.gml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TopoPointType }
     * 
     */
    public TopoPointType createTopoPointType() {
        return new TopoPointType();
    }

    /**
     * Create an instance of {@link ArcStringByBulgeType }
     * 
     */
    public ArcStringByBulgeType createArcStringByBulgeType() {
        return new ArcStringByBulgeType();
    }

    /**
     * Create an instance of {@link OffsetCurveType }
     * 
     */
    public OffsetCurveType createOffsetCurveType() {
        return new OffsetCurveType();
    }

    /**
     * Create an instance of {@link Boolean }
     * 
     */
    public Boolean createBoolean() {
        return new Boolean();
    }

    /**
     * Create an instance of {@link MultiSurfacePropertyType }
     * 
     */
    public MultiSurfacePropertyType createMultiSurfacePropertyType() {
        return new MultiSurfacePropertyType();
    }

    /**
     * Create an instance of {@link Count }
     * 
     */
    public Count createCount() {
        return new Count();
    }

    /**
     * Create an instance of {@link CircleByCenterPointType }
     * 
     */
    public CircleByCenterPointType createCircleByCenterPointType() {
        return new CircleByCenterPointType();
    }

    /**
     * Create an instance of {@link DomainSetType }
     * 
     */
    public DomainSetType createDomainSetType() {
        return new DomainSetType();
    }

    /**
     * Create an instance of {@link TimeIntervalLengthType }
     * 
     */
    public TimeIntervalLengthType createTimeIntervalLengthType() {
        return new TimeIntervalLengthType();
    }

    /**
     * Create an instance of {@link ValuePropertyType }
     * 
     */
    public ValuePropertyType createValuePropertyType() {
        return new ValuePropertyType();
    }

    /**
     * Create an instance of {@link DynamicFeatureType }
     * 
     */
    public DynamicFeatureType createDynamicFeatureType() {
        return new DynamicFeatureType();
    }

    /**
     * Create an instance of {@link LengthType }
     * 
     */
    public LengthType createLengthType() {
        return new LengthType();
    }

    /**
     * Create an instance of {@link CompositeSolidType }
     * 
     */
    public CompositeSolidType createCompositeSolidType() {
        return new CompositeSolidType();
    }

    /**
     * Create an instance of {@link TargetPropertyType }
     * 
     */
    public TargetPropertyType createTargetPropertyType() {
        return new TargetPropertyType();
    }

    /**
     * Create an instance of {@link CylinderType }
     * 
     */
    public CylinderType createCylinderType() {
        return new CylinderType();
    }

    /**
     * Create an instance of {@link GeometryArrayPropertyType }
     * 
     */
    public GeometryArrayPropertyType createGeometryArrayPropertyType() {
        return new GeometryArrayPropertyType();
    }

    /**
     * Create an instance of {@link FileType }
     * 
     */
    public FileType createFileType() {
        return new FileType();
    }

    /**
     * Create an instance of {@link GeometryPropertyType }
     * 
     */
    public GeometryPropertyType createGeometryPropertyType() {
        return new GeometryPropertyType();
    }

    /**
     * Create an instance of {@link LinearRingType }
     * 
     */
    public LinearRingType createLinearRingType() {
        return new LinearRingType();
    }

    /**
     * Create an instance of {@link ConeType }
     * 
     */
    public ConeType createConeType() {
        return new ConeType();
    }

    /**
     * Create an instance of {@link SurfacePropertyType }
     * 
     */
    public SurfacePropertyType createSurfacePropertyType() {
        return new SurfacePropertyType();
    }

    /**
     * Create an instance of {@link CoordinatesType }
     * 
     */
    public CoordinatesType createCoordinatesType() {
        return new CoordinatesType();
    }

    /**
     * Create an instance of {@link GeodesicStringType }
     * 
     */
    public GeodesicStringType createGeodesicStringType() {
        return new GeodesicStringType();
    }

    /**
     * Create an instance of {@link RangeSetType }
     * 
     */
    public RangeSetType createRangeSetType() {
        return new RangeSetType();
    }

    /**
     * Create an instance of {@link TopoSolidPropertyType }
     * 
     */
    public TopoSolidPropertyType createTopoSolidPropertyType() {
        return new TopoSolidPropertyType();
    }

    /**
     * Create an instance of {@link TimeClockPropertyType }
     * 
     */
    public TimeClockPropertyType createTimeClockPropertyType() {
        return new TimeClockPropertyType();
    }

    /**
     * Create an instance of {@link ShellType }
     * 
     */
    public ShellType createShellType() {
        return new ShellType();
    }

    /**
     * Create an instance of {@link TimeReferenceSystemType }
     * 
     */
    public TimeReferenceSystemType createTimeReferenceSystemType() {
        return new TimeReferenceSystemType();
    }

    /**
     * Create an instance of {@link CategoryPropertyType }
     * 
     */
    public CategoryPropertyType createCategoryPropertyType() {
        return new CategoryPropertyType();
    }

    /**
     * Create an instance of {@link BSplineType }
     * 
     */
    public BSplineType createBSplineType() {
        return new BSplineType();
    }

    /**
     * Create an instance of {@link PolygonType }
     * 
     */
    public PolygonType createPolygonType() {
        return new PolygonType();
    }

    /**
     * Create an instance of {@link PointArrayPropertyType }
     * 
     */
    public PointArrayPropertyType createPointArrayPropertyType() {
        return new PointArrayPropertyType();
    }

    /**
     * Create an instance of {@link SequenceRuleType }
     * 
     */
    public SequenceRuleType createSequenceRuleType() {
        return new SequenceRuleType();
    }

    /**
     * Create an instance of {@link TimeOrdinalEraType }
     * 
     */
    public TimeOrdinalEraType createTimeOrdinalEraType() {
        return new TimeOrdinalEraType();
    }

    /**
     * Create an instance of {@link TimeCalendarType }
     * 
     */
    public TimeCalendarType createTimeCalendarType() {
        return new TimeCalendarType();
    }

    /**
     * Create an instance of {@link CodeOrNilReasonListType }
     * 
     */
    public CodeOrNilReasonListType createCodeOrNilReasonListType() {
        return new CodeOrNilReasonListType();
    }

    /**
     * Create an instance of {@link LineStringSegmentArrayPropertyType }
     * 
     */
    public LineStringSegmentArrayPropertyType createLineStringSegmentArrayPropertyType() {
        return new LineStringSegmentArrayPropertyType();
    }

    /**
     * Create an instance of {@link GridType }
     * 
     */
    public GridType createGridType() {
        return new GridType();
    }

    /**
     * Create an instance of {@link AbstractRingPropertyType }
     * 
     */
    public AbstractRingPropertyType createAbstractRingPropertyType() {
        return new AbstractRingPropertyType();
    }

    /**
     * Create an instance of {@link LocationPropertyType }
     * 
     */
    public LocationPropertyType createLocationPropertyType() {
        return new LocationPropertyType();
    }

    /**
     * Create an instance of {@link LinearRingPropertyType }
     * 
     */
    public LinearRingPropertyType createLinearRingPropertyType() {
        return new LinearRingPropertyType();
    }

    /**
     * Create an instance of {@link CurveSegmentArrayPropertyType }
     * 
     */
    public CurveSegmentArrayPropertyType createCurveSegmentArrayPropertyType() {
        return new CurveSegmentArrayPropertyType();
    }

    /**
     * Create an instance of {@link TopoCurveType }
     * 
     */
    public TopoCurveType createTopoCurveType() {
        return new TopoCurveType();
    }

    /**
     * Create an instance of {@link SolidPropertyType }
     * 
     */
    public SolidPropertyType createSolidPropertyType() {
        return new SolidPropertyType();
    }

    /**
     * Create an instance of {@link TopoComplexPropertyType }
     * 
     */
    public TopoComplexPropertyType createTopoComplexPropertyType() {
        return new TopoComplexPropertyType();
    }

    /**
     * Create an instance of {@link CircleType }
     * 
     */
    public CircleType createCircleType() {
        return new CircleType();
    }

    /**
     * Create an instance of {@link ReferenceType }
     * 
     */
    public ReferenceType createReferenceType() {
        return new ReferenceType();
    }

    /**
     * Create an instance of {@link MultiGeometryPropertyType }
     * 
     */
    public MultiGeometryPropertyType createMultiGeometryPropertyType() {
        return new MultiGeometryPropertyType();
    }

    /**
     * Create an instance of {@link AbstractGriddedSurfaceType.Rows.Row }
     * 
     */
    public AbstractGriddedSurfaceType.Rows.Row createAbstractGriddedSurfaceTypeRowsRow() {
        return new AbstractGriddedSurfaceType.Rows.Row();
    }

    /**
     * Create an instance of {@link FaceOrTopoSolidPropertyType }
     * 
     */
    public FaceOrTopoSolidPropertyType createFaceOrTopoSolidPropertyType() {
        return new FaceOrTopoSolidPropertyType();
    }

    /**
     * Create an instance of {@link TimePrimitivePropertyType }
     * 
     */
    public TimePrimitivePropertyType createTimePrimitivePropertyType() {
        return new TimePrimitivePropertyType();
    }

    /**
     * Create an instance of {@link CodeWithAuthorityType }
     * 
     */
    public CodeWithAuthorityType createCodeWithAuthorityType() {
        return new CodeWithAuthorityType();
    }

    /**
     * Create an instance of {@link DirectionPropertyType }
     * 
     */
    public DirectionPropertyType createDirectionPropertyType() {
        return new DirectionPropertyType();
    }

    /**
     * Create an instance of {@link TriangleType }
     * 
     */
    public TriangleType createTriangleType() {
        return new TriangleType();
    }

    /**
     * Create an instance of {@link AffinePlacementType }
     * 
     */
    public AffinePlacementType createAffinePlacementType() {
        return new AffinePlacementType();
    }

    /**
     * Create an instance of {@link DirectedObservationType }
     * 
     */
    public DirectedObservationType createDirectedObservationType() {
        return new DirectedObservationType();
    }

    /**
     * Create an instance of {@link NodeOrEdgePropertyType }
     * 
     */
    public NodeOrEdgePropertyType createNodeOrEdgePropertyType() {
        return new NodeOrEdgePropertyType();
    }

    /**
     * Create an instance of {@link QuantityPropertyType }
     * 
     */
    public QuantityPropertyType createQuantityPropertyType() {
        return new QuantityPropertyType();
    }

    /**
     * Create an instance of {@link EdgeType }
     * 
     */
    public EdgeType createEdgeType() {
        return new EdgeType();
    }

    /**
     * Create an instance of {@link MultiPointType }
     * 
     */
    public MultiPointType createMultiPointType() {
        return new MultiPointType();
    }

    /**
     * Create an instance of {@link TimeCoordinateSystemType }
     * 
     */
    public TimeCoordinateSystemType createTimeCoordinateSystemType() {
        return new TimeCoordinateSystemType();
    }

    /**
     * Create an instance of {@link StringOrRefType }
     * 
     */
    public StringOrRefType createStringOrRefType() {
        return new StringOrRefType();
    }

    /**
     * Create an instance of {@link GeometricPrimitivePropertyType }
     * 
     */
    public GeometricPrimitivePropertyType createGeometricPrimitivePropertyType() {
        return new GeometricPrimitivePropertyType();
    }

    /**
     * Create an instance of {@link TopoVolumeType }
     * 
     */
    public TopoVolumeType createTopoVolumeType() {
        return new TopoVolumeType();
    }

    /**
     * Create an instance of {@link MeasureType }
     * 
     */
    public MeasureType createMeasureType() {
        return new MeasureType();
    }

    /**
     * Create an instance of {@link DirectedObservationAtDistanceType }
     * 
     */
    public DirectedObservationAtDistanceType createDirectedObservationAtDistanceType() {
        return new DirectedObservationAtDistanceType();
    }

    /**
     * Create an instance of {@link NodePropertyType }
     * 
     */
    public NodePropertyType createNodePropertyType() {
        return new NodePropertyType();
    }

    /**
     * Create an instance of {@link TimeNodePropertyType }
     * 
     */
    public TimeNodePropertyType createTimeNodePropertyType() {
        return new TimeNodePropertyType();
    }

    /**
     * Create an instance of {@link PolygonPatchType }
     * 
     */
    public PolygonPatchType createPolygonPatchType() {
        return new PolygonPatchType();
    }

    /**
     * Create an instance of {@link DirectPositionType }
     * 
     */
    public DirectPositionType createDirectPositionType() {
        return new DirectPositionType();
    }

    /**
     * Create an instance of {@link RelatedTimeType }
     * 
     */
    public RelatedTimeType createRelatedTimeType() {
        return new RelatedTimeType();
    }

    /**
     * Create an instance of {@link EnvelopeWithTimePeriodType }
     * 
     */
    public EnvelopeWithTimePeriodType createEnvelopeWithTimePeriodType() {
        return new EnvelopeWithTimePeriodType();
    }

    /**
     * Create an instance of {@link AbstractSolidType }
     * 
     */
    public AbstractSolidType createAbstractSolidType() {
        return new AbstractSolidType();
    }

    /**
     * Create an instance of {@link Category }
     * 
     */
    public Category createCategory() {
        return new Category();
    }

    /**
     * Create an instance of {@link DefinitionType }
     * 
     */
    public DefinitionType createDefinitionType() {
        return new DefinitionType();
    }

    /**
     * Create an instance of {@link ArcByCenterPointType }
     * 
     */
    public ArcByCenterPointType createArcByCenterPointType() {
        return new ArcByCenterPointType();
    }

    /**
     * Create an instance of {@link HistoryPropertyType }
     * 
     */
    public HistoryPropertyType createHistoryPropertyType() {
        return new HistoryPropertyType();
    }

    /**
     * Create an instance of {@link CubicSplineType }
     * 
     */
    public CubicSplineType createCubicSplineType() {
        return new CubicSplineType();
    }

    /**
     * Create an instance of {@link ArcType }
     * 
     */
    public ArcType createArcType() {
        return new ArcType();
    }

    /**
     * Create an instance of {@link TimeType }
     * 
     */
    public TimeType createTimeType() {
        return new TimeType();
    }

    /**
     * Create an instance of {@link TimeEdgeType }
     * 
     */
    public TimeEdgeType createTimeEdgeType() {
        return new TimeEdgeType();
    }

    /**
     * Create an instance of {@link EnvelopeType }
     * 
     */
    public EnvelopeType createEnvelopeType() {
        return new EnvelopeType();
    }

    /**
     * Create an instance of {@link TopoCurvePropertyType }
     * 
     */
    public TopoCurvePropertyType createTopoCurvePropertyType() {
        return new TopoCurvePropertyType();
    }

    /**
     * Create an instance of {@link OrientableCurveType }
     * 
     */
    public OrientableCurveType createOrientableCurveType() {
        return new OrientableCurveType();
    }

    /**
     * Create an instance of {@link VectorType }
     * 
     */
    public VectorType createVectorType() {
        return new VectorType();
    }

    /**
     * Create an instance of {@link TimePeriodPropertyType }
     * 
     */
    public TimePeriodPropertyType createTimePeriodPropertyType() {
        return new TimePeriodPropertyType();
    }

    /**
     * Create an instance of {@link FaceType }
     * 
     */
    public FaceType createFaceType() {
        return new FaceType();
    }

    /**
     * Create an instance of {@link TinType.ControlPoint }
     * 
     */
    public TinType.ControlPoint createTinTypeControlPoint() {
        return new TinType.ControlPoint();
    }

    /**
     * Create an instance of {@link ClothoidType }
     * 
     */
    public ClothoidType createClothoidType() {
        return new ClothoidType();
    }

    /**
     * Create an instance of {@link ValueArrayType }
     * 
     */
    public ValueArrayType createValueArrayType() {
        return new ValueArrayType();
    }

    /**
     * Create an instance of {@link DynamicFeatureMemberType }
     * 
     */
    public DynamicFeatureMemberType createDynamicFeatureMemberType() {
        return new DynamicFeatureMemberType();
    }

    /**
     * Create an instance of {@link SurfaceType }
     * 
     */
    public SurfaceType createSurfaceType() {
        return new SurfaceType();
    }

    /**
     * Create an instance of {@link DirectPositionListType }
     * 
     */
    public DirectPositionListType createDirectPositionListType() {
        return new DirectPositionListType();
    }

    /**
     * Create an instance of {@link TopoSurfacePropertyType }
     * 
     */
    public TopoSurfacePropertyType createTopoSurfacePropertyType() {
        return new TopoSurfacePropertyType();
    }

    /**
     * Create an instance of {@link BoundingShapeType }
     * 
     */
    public BoundingShapeType createBoundingShapeType() {
        return new BoundingShapeType();
    }

    /**
     * Create an instance of {@link MultiSolidType }
     * 
     */
    public MultiSolidType createMultiSolidType() {
        return new MultiSolidType();
    }

    /**
     * Create an instance of {@link TimeClockType }
     * 
     */
    public TimeClockType createTimeClockType() {
        return new TimeClockType();
    }

    /**
     * Create an instance of {@link ClothoidType.RefLocation }
     * 
     */
    public ClothoidType.RefLocation createClothoidTypeRefLocation() {
        return new ClothoidType.RefLocation();
    }

    /**
     * Create an instance of {@link SpeedType }
     * 
     */
    public SpeedType createSpeedType() {
        return new SpeedType();
    }

    /**
     * Create an instance of {@link MappingRuleType }
     * 
     */
    public MappingRuleType createMappingRuleType() {
        return new MappingRuleType();
    }

    /**
     * Create an instance of {@link TopoPointPropertyType }
     * 
     */
    public TopoPointPropertyType createTopoPointPropertyType() {
        return new TopoPointPropertyType();
    }

    /**
     * Create an instance of {@link CodeListType }
     * 
     */
    public CodeListType createCodeListType() {
        return new CodeListType();
    }

    /**
     * Create an instance of {@link ProcedurePropertyType }
     * 
     */
    public ProcedurePropertyType createProcedurePropertyType() {
        return new ProcedurePropertyType();
    }

    /**
     * Create an instance of {@link RectifiedGridType }
     * 
     */
    public RectifiedGridType createRectifiedGridType() {
        return new RectifiedGridType();
    }

    /**
     * Create an instance of {@link MultiCurveType }
     * 
     */
    public MultiCurveType createMultiCurveType() {
        return new MultiCurveType();
    }

    /**
     * Create an instance of {@link SolidType }
     * 
     */
    public SolidType createSolidType() {
        return new SolidType();
    }

    /**
     * Create an instance of {@link NodeType }
     * 
     */
    public NodeType createNodeType() {
        return new NodeType();
    }

    /**
     * Create an instance of {@link CoverageFunctionType }
     * 
     */
    public CoverageFunctionType createCoverageFunctionType() {
        return new CoverageFunctionType();
    }

    /**
     * Create an instance of {@link KnotType }
     * 
     */
    public KnotType createKnotType() {
        return new KnotType();
    }

    /**
     * Create an instance of {@link TopoPrimitiveArrayAssociationType }
     * 
     */
    public TopoPrimitiveArrayAssociationType createTopoPrimitiveArrayAssociationType() {
        return new TopoPrimitiveArrayAssociationType();
    }

    /**
     * Create an instance of {@link DefinitionBaseType }
     * 
     */
    public DefinitionBaseType createDefinitionBaseType() {
        return new DefinitionBaseType();
    }

    /**
     * Create an instance of {@link DirectedEdgePropertyType }
     * 
     */
    public DirectedEdgePropertyType createDirectedEdgePropertyType() {
        return new DirectedEdgePropertyType();
    }

    /**
     * Create an instance of {@link CodeType }
     * 
     */
    public CodeType createCodeType() {
        return new CodeType();
    }

    /**
     * Create an instance of {@link TimeCalendarEraPropertyType }
     * 
     */
    public TimeCalendarEraPropertyType createTimeCalendarEraPropertyType() {
        return new TimeCalendarEraPropertyType();
    }

    /**
     * Create an instance of {@link UnitOfMeasureType }
     * 
     */
    public UnitOfMeasureType createUnitOfMeasureType() {
        return new UnitOfMeasureType();
    }

    /**
     * Create an instance of {@link DerivedUnitType }
     * 
     */
    public DerivedUnitType createDerivedUnitType() {
        return new DerivedUnitType();
    }

    /**
     * Create an instance of {@link TinType }
     * 
     */
    public TinType createTinType() {
        return new TinType();
    }

    /**
     * Create an instance of {@link CompositeValueType }
     * 
     */
    public CompositeValueType createCompositeValueType() {
        return new CompositeValueType();
    }

    /**
     * Create an instance of {@link DataBlockType }
     * 
     */
    public DataBlockType createDataBlockType() {
        return new DataBlockType();
    }

    /**
     * Create an instance of {@link UnitDefinitionType }
     * 
     */
    public UnitDefinitionType createUnitDefinitionType() {
        return new UnitDefinitionType();
    }

    /**
     * Create an instance of {@link AngleType }
     * 
     */
    public AngleType createAngleType() {
        return new AngleType();
    }

    /**
     * Create an instance of {@link MetaDataPropertyType }
     * 
     */
    public MetaDataPropertyType createMetaDataPropertyType() {
        return new MetaDataPropertyType();
    }

    /**
     * Create an instance of {@link CategoryExtentType }
     * 
     */
    public CategoryExtentType createCategoryExtentType() {
        return new CategoryExtentType();
    }

    /**
     * Create an instance of {@link CountPropertyType }
     * 
     */
    public CountPropertyType createCountPropertyType() {
        return new CountPropertyType();
    }

    /**
     * Create an instance of {@link RectangleType }
     * 
     */
    public RectangleType createRectangleType() {
        return new RectangleType();
    }

    /**
     * Create an instance of {@link SurfaceArrayPropertyType }
     * 
     */
    public SurfaceArrayPropertyType createSurfaceArrayPropertyType() {
        return new SurfaceArrayPropertyType();
    }

    /**
     * Create an instance of {@link GeometricComplexType }
     * 
     */
    public GeometricComplexType createGeometricComplexType() {
        return new GeometricComplexType();
    }

    /**
     * Create an instance of {@link GridLimitsType }
     * 
     */
    public GridLimitsType createGridLimitsType() {
        return new GridLimitsType();
    }

    /**
     * Create an instance of {@link MeasureOrNilReasonListType }
     * 
     */
    public MeasureOrNilReasonListType createMeasureOrNilReasonListType() {
        return new MeasureOrNilReasonListType();
    }

    /**
     * Create an instance of {@link MultiSolidPropertyType }
     * 
     */
    public MultiSolidPropertyType createMultiSolidPropertyType() {
        return new MultiSolidPropertyType();
    }

    /**
     * Create an instance of {@link DictionaryType }
     * 
     */
    public DictionaryType createDictionaryType() {
        return new DictionaryType();
    }

    /**
     * Create an instance of {@link AssociationRoleType }
     * 
     */
    public AssociationRoleType createAssociationRoleType() {
        return new AssociationRoleType();
    }

    /**
     * Create an instance of {@link TimeInstantPropertyType }
     * 
     */
    public TimeInstantPropertyType createTimeInstantPropertyType() {
        return new TimeInstantPropertyType();
    }

    /**
     * Create an instance of {@link TimeInstantType }
     * 
     */
    public TimeInstantType createTimeInstantType() {
        return new TimeInstantType();
    }

    /**
     * Create an instance of {@link DirectedFacePropertyType }
     * 
     */
    public DirectedFacePropertyType createDirectedFacePropertyType() {
        return new DirectedFacePropertyType();
    }

    /**
     * Create an instance of {@link TimeCalendarEraType }
     * 
     */
    public TimeCalendarEraType createTimeCalendarEraType() {
        return new TimeCalendarEraType();
    }

    /**
     * Create an instance of {@link TimeTopologyPrimitivePropertyType }
     * 
     */
    public TimeTopologyPrimitivePropertyType createTimeTopologyPrimitivePropertyType() {
        return new TimeTopologyPrimitivePropertyType();
    }

    /**
     * Create an instance of {@link TimeTopologyComplexPropertyType }
     * 
     */
    public TimeTopologyComplexPropertyType createTimeTopologyComplexPropertyType() {
        return new TimeTopologyComplexPropertyType();
    }

    /**
     * Create an instance of {@link TopoSurfaceType }
     * 
     */
    public TopoSurfaceType createTopoSurfaceType() {
        return new TopoSurfaceType();
    }

    /**
     * Create an instance of {@link RingType }
     * 
     */
    public RingType createRingType() {
        return new RingType();
    }

    /**
     * Create an instance of {@link ShellPropertyType }
     * 
     */
    public ShellPropertyType createShellPropertyType() {
        return new ShellPropertyType();
    }

    /**
     * Create an instance of {@link GeodesicType }
     * 
     */
    public GeodesicType createGeodesicType() {
        return new GeodesicType();
    }

    /**
     * Create an instance of {@link TimeOrdinalEraPropertyType }
     * 
     */
    public TimeOrdinalEraPropertyType createTimeOrdinalEraPropertyType() {
        return new TimeOrdinalEraPropertyType();
    }

    /**
     * Create an instance of {@link SurfacePatchArrayPropertyType }
     * 
     */
    public SurfacePatchArrayPropertyType createSurfacePatchArrayPropertyType() {
        return new SurfacePatchArrayPropertyType();
    }

    /**
     * Create an instance of {@link DirectedTopoSolidPropertyType }
     * 
     */
    public DirectedTopoSolidPropertyType createDirectedTopoSolidPropertyType() {
        return new DirectedTopoSolidPropertyType();
    }

    /**
     * Create an instance of {@link CurvePropertyType }
     * 
     */
    public CurvePropertyType createCurvePropertyType() {
        return new CurvePropertyType();
    }

    /**
     * Create an instance of {@link QuantityExtentType }
     * 
     */
    public QuantityExtentType createQuantityExtentType() {
        return new QuantityExtentType();
    }

    /**
     * Create an instance of {@link VolumeType }
     * 
     */
    public VolumeType createVolumeType() {
        return new VolumeType();
    }

    /**
     * Create an instance of {@link TimeOrdinalReferenceSystemType }
     * 
     */
    public TimeOrdinalReferenceSystemType createTimeOrdinalReferenceSystemType() {
        return new TimeOrdinalReferenceSystemType();
    }

    /**
     * Create an instance of {@link OrientableSurfaceType }
     * 
     */
    public OrientableSurfaceType createOrientableSurfaceType() {
        return new OrientableSurfaceType();
    }

    /**
     * Create an instance of {@link BooleanPropertyType }
     * 
     */
    public BooleanPropertyType createBooleanPropertyType() {
        return new BooleanPropertyType();
    }

    /**
     * Create an instance of {@link ResultType }
     * 
     */
    public ResultType createResultType() {
        return new ResultType();
    }

    /**
     * Create an instance of {@link BaseUnitType }
     * 
     */
    public BaseUnitType createBaseUnitType() {
        return new BaseUnitType();
    }

    /**
     * Create an instance of {@link TopoSolidType }
     * 
     */
    public TopoSolidType createTopoSolidType() {
        return new TopoSolidType();
    }

    /**
     * Create an instance of {@link TopoComplexType }
     * 
     */
    public TopoComplexType createTopoComplexType() {
        return new TopoComplexType();
    }

    /**
     * Create an instance of {@link CurveArrayPropertyType }
     * 
     */
    public CurveArrayPropertyType createCurveArrayPropertyType() {
        return new CurveArrayPropertyType();
    }

    /**
     * Create an instance of {@link GridFunctionType }
     * 
     */
    public GridFunctionType createGridFunctionType() {
        return new GridFunctionType();
    }

    /**
     * Create an instance of {@link TimePeriodType }
     * 
     */
    public TimePeriodType createTimePeriodType() {
        return new TimePeriodType();
    }

    /**
     * Create an instance of {@link TimeCalendarPropertyType }
     * 
     */
    public TimeCalendarPropertyType createTimeCalendarPropertyType() {
        return new TimeCalendarPropertyType();
    }

    /**
     * Create an instance of {@link FormulaType }
     * 
     */
    public FormulaType createFormulaType() {
        return new FormulaType();
    }

    /**
     * Create an instance of {@link Quantity }
     * 
     */
    public Quantity createQuantity() {
        return new Quantity();
    }

    /**
     * Create an instance of {@link DerivationUnitTermType }
     * 
     */
    public DerivationUnitTermType createDerivationUnitTermType() {
        return new DerivationUnitTermType();
    }

    /**
     * Create an instance of {@link ObservationType }
     * 
     */
    public ObservationType createObservationType() {
        return new ObservationType();
    }

    /**
     * Create an instance of {@link GridEnvelopeType }
     * 
     */
    public GridEnvelopeType createGridEnvelopeType() {
        return new GridEnvelopeType();
    }

    /**
     * Create an instance of {@link CompositeSurfaceType }
     * 
     */
    public CompositeSurfaceType createCompositeSurfaceType() {
        return new CompositeSurfaceType();
    }

    /**
     * Create an instance of {@link DiscreteCoverageType }
     * 
     */
    public DiscreteCoverageType createDiscreteCoverageType() {
        return new DiscreteCoverageType();
    }

    /**
     * Create an instance of {@link InlinePropertyType }
     * 
     */
    public InlinePropertyType createInlinePropertyType() {
        return new InlinePropertyType();
    }

    /**
     * Create an instance of {@link SolidArrayPropertyType }
     * 
     */
    public SolidArrayPropertyType createSolidArrayPropertyType() {
        return new SolidArrayPropertyType();
    }

    /**
     * Create an instance of {@link KnotPropertyType }
     * 
     */
    public KnotPropertyType createKnotPropertyType() {
        return new KnotPropertyType();
    }

    /**
     * Create an instance of {@link ConventionalUnitType }
     * 
     */
    public ConventionalUnitType createConventionalUnitType() {
        return new ConventionalUnitType();
    }

    /**
     * Create an instance of {@link ArcStringType }
     * 
     */
    public ArcStringType createArcStringType() {
        return new ArcStringType();
    }

    /**
     * Create an instance of {@link CurveType }
     * 
     */
    public CurveType createCurveType() {
        return new CurveType();
    }

    /**
     * Create an instance of {@link DirectedNodePropertyType }
     * 
     */
    public DirectedNodePropertyType createDirectedNodePropertyType() {
        return new DirectedNodePropertyType();
    }

    /**
     * Create an instance of {@link DictionaryEntryType }
     * 
     */
    public DictionaryEntryType createDictionaryEntryType() {
        return new DictionaryEntryType();
    }

    /**
     * Create an instance of {@link TimePositionType }
     * 
     */
    public TimePositionType createTimePositionType() {
        return new TimePositionType();
    }

    /**
     * Create an instance of {@link MultiSurfaceType }
     * 
     */
    public MultiSurfaceType createMultiSurfaceType() {
        return new MultiSurfaceType();
    }

    /**
     * Create an instance of {@link ArcByBulgeType }
     * 
     */
    public ArcByBulgeType createArcByBulgeType() {
        return new ArcByBulgeType();
    }

    /**
     * Create an instance of {@link LineStringType }
     * 
     */
    public LineStringType createLineStringType() {
        return new LineStringType();
    }

    /**
     * Create an instance of {@link ConversionToPreferredUnitType }
     * 
     */
    public ConversionToPreferredUnitType createConversionToPreferredUnitType() {
        return new ConversionToPreferredUnitType();
    }

    /**
     * Create an instance of {@link ValueArrayPropertyType }
     * 
     */
    public ValueArrayPropertyType createValueArrayPropertyType() {
        return new ValueArrayPropertyType();
    }

    /**
     * Create an instance of {@link DirectionDescriptionType }
     * 
     */
    public DirectionDescriptionType createDirectionDescriptionType() {
        return new DirectionDescriptionType();
    }

    /**
     * Create an instance of {@link LineStringSegmentType }
     * 
     */
    public LineStringSegmentType createLineStringSegmentType() {
        return new LineStringSegmentType();
    }

    /**
     * Create an instance of {@link AbstractGriddedSurfaceType.Rows }
     * 
     */
    public AbstractGriddedSurfaceType.Rows createAbstractGriddedSurfaceTypeRows() {
        return new AbstractGriddedSurfaceType.Rows();
    }

    /**
     * Create an instance of {@link FeaturePropertyType }
     * 
     */
    public FeaturePropertyType createFeaturePropertyType() {
        return new FeaturePropertyType();
    }

    /**
     * Create an instance of {@link AreaType }
     * 
     */
    public AreaType createAreaType() {
        return new AreaType();
    }

    /**
     * Create an instance of {@link RingPropertyType }
     * 
     */
    public RingPropertyType createRingPropertyType() {
        return new RingPropertyType();
    }

    /**
     * Create an instance of {@link GeometricComplexPropertyType }
     * 
     */
    public GeometricComplexPropertyType createGeometricComplexPropertyType() {
        return new GeometricComplexPropertyType();
    }

    /**
     * Create an instance of {@link TopoVolumePropertyType }
     * 
     */
    public TopoVolumePropertyType createTopoVolumePropertyType() {
        return new TopoVolumePropertyType();
    }

    /**
     * Create an instance of {@link GridLengthType }
     * 
     */
    public GridLengthType createGridLengthType() {
        return new GridLengthType();
    }

    /**
     * Create an instance of {@link DirectionVectorType }
     * 
     */
    public DirectionVectorType createDirectionVectorType() {
        return new DirectionVectorType();
    }

    /**
     * Create an instance of {@link CompositeCurveType }
     * 
     */
    public CompositeCurveType createCompositeCurveType() {
        return new CompositeCurveType();
    }

    /**
     * Create an instance of {@link TopoPrimitiveMemberType }
     * 
     */
    public TopoPrimitiveMemberType createTopoPrimitiveMemberType() {
        return new TopoPrimitiveMemberType();
    }

    /**
     * Create an instance of {@link PointType }
     * 
     */
    public PointType createPointType() {
        return new PointType();
    }

    /**
     * Create an instance of {@link MultiGeometryType }
     * 
     */
    public MultiGeometryType createMultiGeometryType() {
        return new MultiGeometryType();
    }

    /**
     * Create an instance of {@link TimeEdgePropertyType }
     * 
     */
    public TimeEdgePropertyType createTimeEdgePropertyType() {
        return new TimeEdgePropertyType();
    }

    /**
     * Create an instance of {@link MultiPointPropertyType }
     * 
     */
    public MultiPointPropertyType createMultiPointPropertyType() {
        return new MultiPointPropertyType();
    }

    /**
     * Create an instance of {@link PointPropertyType }
     * 
     */
    public PointPropertyType createPointPropertyType() {
        return new PointPropertyType();
    }

    /**
     * Create an instance of {@link MeasureListType }
     * 
     */
    public MeasureListType createMeasureListType() {
        return new MeasureListType();
    }

    /**
     * Create an instance of {@link TimeNodeType }
     * 
     */
    public TimeNodeType createTimeNodeType() {
        return new TimeNodeType();
    }

    /**
     * Create an instance of {@link ScaleType }
     * 
     */
    public ScaleType createScaleType() {
        return new ScaleType();
    }

    /**
     * Create an instance of {@link MultiCurvePropertyType }
     * 
     */
    public MultiCurvePropertyType createMultiCurvePropertyType() {
        return new MultiCurvePropertyType();
    }

    /**
     * Create an instance of {@link DynamicFeatureCollectionType }
     * 
     */
    public DynamicFeatureCollectionType createDynamicFeatureCollectionType() {
        return new DynamicFeatureCollectionType();
    }

    /**
     * Create an instance of {@link BezierType }
     * 
     */
    public BezierType createBezierType() {
        return new BezierType();
    }

    /**
     * Create an instance of {@link SphereType }
     * 
     */
    public SphereType createSphereType() {
        return new SphereType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CircleByCenterPointType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CircleByCenterPoint", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "ArcByCenterPoint")
    public JAXBElement<CircleByCenterPointType> createCircleByCenterPoint(CircleByCenterPointType value) {
        return new JAXBElement<CircleByCenterPointType>(_CircleByCenterPoint_QNAME, CircleByCenterPointType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SphereType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Sphere", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGriddedSurface")
    public JAXBElement<SphereType> createSphere(SphereType value) {
        return new JAXBElement<SphereType>(_Sphere_QNAME, SphereType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractParametricCurveSurfaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractParametricCurveSurface", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractSurfacePatch")
    public JAXBElement<AbstractParametricCurveSurfaceType> createAbstractParametricCurveSurface(AbstractParametricCurveSurfaceType value) {
        return new JAXBElement<AbstractParametricCurveSurfaceType>(_AbstractParametricCurveSurface_QNAME, AbstractParametricCurveSurfaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractSurfacePatchType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractSurfacePatch")
    public JAXBElement<AbstractSurfacePatchType> createAbstractSurfacePatch(AbstractSurfacePatchType value) {
        return new JAXBElement<AbstractSurfacePatchType>(_AbstractSurfacePatch_QNAME, AbstractSurfacePatchType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DirectedObservationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "DirectedObservation", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "Observation")
    public JAXBElement<DirectedObservationType> createDirectedObservation(DirectedObservationType value) {
        return new JAXBElement<DirectedObservationType>(_DirectedObservation_QNAME, DirectedObservationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "quantityTypeReference")
    public JAXBElement<ReferenceType> createQuantityTypeReference(ReferenceType value) {
        return new JAXBElement<ReferenceType>(_QuantityTypeReference_QNAME, ReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompositeCurveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CompositeCurve", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurve")
    public JAXBElement<CompositeCurveType> createCompositeCurve(CompositeCurveType value) {
        return new JAXBElement<CompositeCurveType>(_CompositeCurve_QNAME, CompositeCurveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BoundingShapeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "boundedBy")
    public JAXBElement<BoundingShapeType> createBoundedBy(BoundingShapeType value) {
        return new JAXBElement<BoundingShapeType>(_BoundedBy_QNAME, BoundingShapeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArcStringType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "ArcString", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurveSegment")
    public JAXBElement<ArcStringType> createArcString(ArcStringType value) {
        return new JAXBElement<ArcStringType>(_ArcString_QNAME, ArcStringType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoPointType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TopoPoint")
    public JAXBElement<TopoPointType> createTopoPoint(TopoPointType value) {
        return new JAXBElement<TopoPointType>(_TopoPoint_QNAME, TopoPointType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiSurfacePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "multiExtentOf")
    public JAXBElement<MultiSurfacePropertyType> createMultiExtentOf(MultiSurfacePropertyType value) {
        return new JAXBElement<MultiSurfacePropertyType>(_MultiExtentOf_QNAME, MultiSurfacePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DirectedNodePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "directedNode")
    public JAXBElement<DirectedNodePropertyType> createDirectedNode(DirectedNodePropertyType value) {
        return new JAXBElement<DirectedNodePropertyType>(_DirectedNode_QNAME, DirectedNodePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractScalarValueList", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractValue")
    public JAXBElement<Object> createAbstractScalarValueList(Object value) {
        return new JAXBElement<Object>(_AbstractScalarValueList_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoPrimitiveMemberType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "topoPrimitiveMember")
    public JAXBElement<TopoPrimitiveMemberType> createTopoPrimitiveMember(TopoPrimitiveMemberType value) {
        return new JAXBElement<TopoPrimitiveMemberType>(_TopoPrimitiveMember_QNAME, TopoPrimitiveMemberType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiPointType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "MultiPoint", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometricAggregate")
    public JAXBElement<MultiPointType> createMultiPoint(MultiPointType value) {
        return new JAXBElement<MultiPointType>(_MultiPoint_QNAME, MultiPointType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "abstractReference")
    public JAXBElement<ReferenceType> createAbstractReference(ReferenceType value) {
        return new JAXBElement<ReferenceType>(_AbstractReference_QNAME, ReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscreteCoverageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "MultiSurfaceCoverage", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractDiscreteCoverage")
    public JAXBElement<DiscreteCoverageType> createMultiSurfaceCoverage(DiscreteCoverageType value) {
        return new JAXBElement<DiscreteCoverageType>(_MultiSurfaceCoverage_QNAME, DiscreteCoverageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "targetElement")
    public JAXBElement<String> createTargetElement(String value) {
        return new JAXBElement<String>(_TargetElement_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VectorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "vector")
    public JAXBElement<VectorType> createVector(VectorType value) {
        return new JAXBElement<VectorType>(_Vector_QNAME, VectorType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Count }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Count", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractScalarValue")
    public JAXBElement<Count> createCount(Count value) {
        return new JAXBElement<Count>(_Count_QNAME, Count.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DirectionPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "direction")
    public JAXBElement<DirectionPropertyType> createDirection(DirectionPropertyType value) {
        return new JAXBElement<DirectionPropertyType>(_Direction_QNAME, DirectionPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TargetPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "target")
    public JAXBElement<TargetPropertyType> createTarget(TargetPropertyType value) {
        return new JAXBElement<TargetPropertyType>(_Target_QNAME, TargetPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnvelopeWithTimePeriodType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "EnvelopeWithTimePeriod", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "Envelope")
    public JAXBElement<EnvelopeWithTimePeriodType> createEnvelopeWithTimePeriod(EnvelopeWithTimePeriodType value) {
        return new JAXBElement<EnvelopeWithTimePeriodType>(_EnvelopeWithTimePeriod_QNAME, EnvelopeWithTimePeriodType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArcByBulgeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "ArcByBulge", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "ArcStringByBulge")
    public JAXBElement<ArcByBulgeType> createArcByBulge(ArcByBulgeType value) {
        return new JAXBElement<ArcByBulgeType>(_ArcByBulge_QNAME, ArcByBulgeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoVolumeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TopoVolume")
    public JAXBElement<TopoVolumeType> createTopoVolume(TopoVolumeType value) {
        return new JAXBElement<TopoVolumeType>(_TopoVolume_QNAME, TopoVolumeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringOrRefType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "MappingRule")
    public JAXBElement<StringOrRefType> createMappingRule(StringOrRefType value) {
        return new JAXBElement<StringOrRefType>(_MappingRule_QNAME, StringOrRefType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "dataSourceReference")
    public JAXBElement<ReferenceType> createDataSourceReference(ReferenceType value) {
        return new JAXBElement<ReferenceType>(_DataSourceReference_QNAME, ReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PointArrayPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "pointMembers")
    public JAXBElement<PointArrayPropertyType> createPointMembers(PointArrayPropertyType value) {
        return new JAXBElement<PointArrayPropertyType>(_PointMembers_QNAME, PointArrayPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Quantity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Quantity", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractScalarValue")
    public JAXBElement<Quantity> createQuantity(Quantity value) {
        return new JAXBElement<Quantity>(_Quantity_QNAME, Quantity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataBlockType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "DataBlock", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<DataBlockType> createDataBlock(DataBlockType value) {
        return new JAXBElement<DataBlockType>(_DataBlock_QNAME, DataBlockType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "File", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<FileType> createFile(FileType value) {
        return new JAXBElement<FileType>(_File_QNAME, FileType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "locationReference")
    public JAXBElement<ReferenceType> createLocationReference(ReferenceType value) {
        return new JAXBElement<ReferenceType>(_LocationReference_QNAME, ReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoSurfaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TopoSurface")
    public JAXBElement<TopoSurfaceType> createTopoSurface(TopoSurfaceType value) {
        return new JAXBElement<TopoSurfaceType>(_TopoSurface_QNAME, TopoSurfaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Cone", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGriddedSurface")
    public JAXBElement<ConeType> createCone(ConeType value) {
        return new JAXBElement<ConeType>(_Cone_QNAME, ConeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompositeValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CompositeValue", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractValue")
    public JAXBElement<CompositeValueType> createCompositeValue(CompositeValueType value) {
        return new JAXBElement<CompositeValueType>(_CompositeValue_QNAME, CompositeValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeReferenceSystemType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeReferenceSystem", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "Definition")
    public JAXBElement<TimeReferenceSystemType> createTimeReferenceSystem(TimeReferenceSystemType value) {
        return new JAXBElement<TimeReferenceSystemType>(_TimeReferenceSystem_QNAME, TimeReferenceSystemType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractMetaDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractMetaData", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<AbstractMetaDataType> createAbstractMetaData(AbstractMetaDataType value) {
        return new JAXBElement<AbstractMetaDataType>(_AbstractMetaData_QNAME, AbstractMetaDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimePositionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "timePosition")
    public JAXBElement<TimePositionType> createTimePosition(TimePositionType value) {
        return new JAXBElement<TimePositionType>(_TimePosition_QNAME, TimePositionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractRingType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractRing", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<AbstractRingType> createAbstractRing(AbstractRingType value) {
        return new JAXBElement<AbstractRingType>(_AbstractRing_QNAME, AbstractRingType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DirectedTopoSolidPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "directedTopoSolid")
    public JAXBElement<DirectedTopoSolidPropertyType> createDirectedTopoSolid(DirectedTopoSolidPropertyType value) {
        return new JAXBElement<DirectedTopoSolidPropertyType>(_DirectedTopoSolid_QNAME, DirectedTopoSolidPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArcStringByBulgeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "ArcStringByBulge", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurveSegment")
    public JAXBElement<ArcStringByBulgeType> createArcStringByBulge(ArcStringByBulgeType value) {
        return new JAXBElement<ArcStringByBulgeType>(_ArcStringByBulge_QNAME, ArcStringByBulgeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeEdgeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeEdge", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTimeTopologyPrimitive")
    public JAXBElement<TimeEdgeType> createTimeEdge(TimeEdgeType value) {
        return new JAXBElement<TimeEdgeType>(_TimeEdge_QNAME, TimeEdgeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscreteCoverageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "MultiPointCoverage", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractDiscreteCoverage")
    public JAXBElement<DiscreteCoverageType> createMultiPointCoverage(DiscreteCoverageType value) {
        return new JAXBElement<DiscreteCoverageType>(_MultiPointCoverage_QNAME, DiscreteCoverageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DynamicFeatureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "DynamicFeature", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractFeature")
    public JAXBElement<DynamicFeatureType> createDynamicFeature(DynamicFeatureType value) {
        return new JAXBElement<DynamicFeatureType>(_DynamicFeature_QNAME, DynamicFeatureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DictionaryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Dictionary", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "Definition")
    public JAXBElement<DictionaryType> createDictionary(DictionaryType value) {
        return new JAXBElement<DictionaryType>(_Dictionary_QNAME, DictionaryType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "descriptionReference")
    public JAXBElement<ReferenceType> createDescriptionReference(ReferenceType value) {
        return new JAXBElement<ReferenceType>(_DescriptionReference_QNAME, ReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurvePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "baseCurve")
    public JAXBElement<CurvePropertyType> createBaseCurve(CurvePropertyType value) {
        return new JAXBElement<CurvePropertyType>(_BaseCurve_QNAME, CurvePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PointPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "pointMember")
    public JAXBElement<PointPropertyType> createPointMember(PointPropertyType value) {
        return new JAXBElement<PointPropertyType>(_PointMember_QNAME, PointPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValueArrayType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "ValueArray", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "CompositeValue")
    public JAXBElement<ValueArrayType> createValueArray(ValueArrayType value) {
        return new JAXBElement<ValueArrayType>(_ValueArray_QNAME, ValueArrayType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractSurfaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractSurface", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometricPrimitive")
    public JAXBElement<AbstractSurfaceType> createAbstractSurface(AbstractSurfaceType value) {
        return new JAXBElement<AbstractSurfaceType>(_AbstractSurface_QNAME, AbstractSurfaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SurfacePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "baseSurface")
    public JAXBElement<SurfacePropertyType> createBaseSurface(SurfacePropertyType value) {
        return new JAXBElement<SurfacePropertyType>(_BaseSurface_QNAME, SurfacePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeOrdinalReferenceSystemType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeOrdinalReferenceSystem", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "TimeReferenceSystem")
    public JAXBElement<TimeOrdinalReferenceSystemType> createTimeOrdinalReferenceSystem(TimeOrdinalReferenceSystemType value) {
        return new JAXBElement<TimeOrdinalReferenceSystemType>(_TimeOrdinalReferenceSystem_QNAME, TimeOrdinalReferenceSystemType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LineStringType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "LineString", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurve")
    public JAXBElement<LineStringType> createLineString(LineStringType value) {
        return new JAXBElement<LineStringType>(_LineString_QNAME, LineStringType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObservationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Observation", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractFeature")
    public JAXBElement<ObservationType> createObservation(ObservationType value) {
        return new JAXBElement<ObservationType>(_Observation_QNAME, ObservationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoSurfacePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "topoSurfaceProperty")
    public JAXBElement<TopoSurfacePropertyType> createTopoSurfaceProperty(TopoSurfacePropertyType value) {
        return new JAXBElement<TopoSurfacePropertyType>(_TopoSurfaceProperty_QNAME, TopoSurfacePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BaseUnitType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "BaseUnit", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "UnitDefinition")
    public JAXBElement<BaseUnitType> createBaseUnit(BaseUnitType value) {
        return new JAXBElement<BaseUnitType>(_BaseUnit_QNAME, BaseUnitType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompositeSolidType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CompositeSolid", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractSolid")
    public JAXBElement<CompositeSolidType> createCompositeSolid(CompositeSolidType value) {
        return new JAXBElement<CompositeSolidType>(_CompositeSolid_QNAME, CompositeSolidType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnvelopeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Envelope", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<EnvelopeType> createEnvelope(EnvelopeType value) {
        return new JAXBElement<EnvelopeType>(_Envelope_QNAME, EnvelopeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnitOfMeasureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "unitOfMeasure")
    public JAXBElement<UnitOfMeasureType> createUnitOfMeasure(UnitOfMeasureType value) {
        return new JAXBElement<UnitOfMeasureType>(_UnitOfMeasure_QNAME, UnitOfMeasureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoComplexPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "maximalComplex")
    public JAXBElement<TopoComplexPropertyType> createMaximalComplex(TopoComplexPropertyType value) {
        return new JAXBElement<TopoComplexPropertyType>(_MaximalComplex_QNAME, TopoComplexPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LinearRingType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "LinearRing", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractRing")
    public JAXBElement<LinearRingType> createLinearRing(LinearRingType value) {
        return new JAXBElement<LinearRingType>(_LinearRing_QNAME, LinearRingType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractGMLType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractGML", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<AbstractGMLType> createAbstractGML(AbstractGMLType value) {
        return new JAXBElement<AbstractGMLType>(_AbstractGML_QNAME, AbstractGMLType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DynamicFeatureCollectionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "DynamicFeatureCollection", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "DynamicFeature")
    public JAXBElement<DynamicFeatureCollectionType> createDynamicFeatureCollection(DynamicFeatureCollectionType value) {
        return new JAXBElement<DynamicFeatureCollectionType>(_DynamicFeatureCollection_QNAME, DynamicFeatureCollectionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeWithAuthorityType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "identifier")
    public JAXBElement<CodeWithAuthorityType> createIdentifier(CodeWithAuthorityType value) {
        return new JAXBElement<CodeWithAuthorityType>(_Identifier_QNAME, CodeWithAuthorityType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BSplineType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "BSpline", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurveSegment")
    public JAXBElement<BSplineType> createBSpline(BSplineType value) {
        return new JAXBElement<BSplineType>(_BSpline_QNAME, BSplineType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiGeometryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "MultiGeometry", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometricAggregate")
    public JAXBElement<MultiGeometryType> createMultiGeometry(MultiGeometryType value) {
        return new JAXBElement<MultiGeometryType>(_MultiGeometry_QNAME, MultiGeometryType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TopoComplex", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTopology")
    public JAXBElement<TopoComplexType> createTopoComplex(TopoComplexType value) {
        return new JAXBElement<TopoComplexType>(_TopoComplex_QNAME, TopoComplexType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MappingRuleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CoverageMappingRule", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<MappingRuleType> createCoverageMappingRule(MappingRuleType value) {
        return new JAXBElement<MappingRuleType>(_CoverageMappingRule_QNAME, MappingRuleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrientableCurveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "OrientableCurve", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurve")
    public JAXBElement<OrientableCurveType> createOrientableCurve(OrientableCurveType value) {
        return new JAXBElement<OrientableCurveType>(_OrientableCurve_QNAME, OrientableCurveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SurfacePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "surfaceMember")
    public JAXBElement<SurfacePropertyType> createSurfaceMember(SurfacePropertyType value) {
        return new JAXBElement<SurfacePropertyType>(_SurfaceMember_QNAME, SurfacePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolidType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Solid", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractSolid")
    public JAXBElement<SolidType> createSolid(SolidType value) {
        return new JAXBElement<SolidType>(_Solid_QNAME, SolidType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscreteCoverageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "MultiCurveCoverage", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractDiscreteCoverage")
    public JAXBElement<DiscreteCoverageType> createMultiCurveCoverage(DiscreteCoverageType value) {
        return new JAXBElement<DiscreteCoverageType>(_MultiCurveCoverage_QNAME, DiscreteCoverageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "associationName")
    public JAXBElement<String> createAssociationName(String value) {
        return new JAXBElement<String>(_AssociationName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Category }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Category", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractScalarValue")
    public JAXBElement<Category> createCategory(Category value) {
        return new JAXBElement<Category>(_Category_QNAME, Category.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiSolidType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "MultiSolid", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometricAggregate")
    public JAXBElement<MultiSolidType> createMultiSolid(MultiSolidType value) {
        return new JAXBElement<MultiSolidType>(_MultiSolid_QNAME, MultiSolidType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTimePrimitiveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractTimePrimitive", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTimeObject")
    public JAXBElement<AbstractTimePrimitiveType> createAbstractTimePrimitive(AbstractTimePrimitiveType value) {
        return new JAXBElement<AbstractTimePrimitiveType>(_AbstractTimePrimitive_QNAME, AbstractTimePrimitiveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeTopologyComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeTopologyComplex", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTimeComplex")
    public JAXBElement<TimeTopologyComplexType> createTimeTopologyComplex(TimeTopologyComplexType value) {
        return new JAXBElement<TimeTopologyComplexType>(_TimeTopologyComplex_QNAME, TimeTopologyComplexType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArcByCenterPointType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "ArcByCenterPoint", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurveSegment")
    public JAXBElement<ArcByCenterPointType> createArcByCenterPoint(ArcByCenterPointType value) {
        return new JAXBElement<ArcByCenterPointType>(_ArcByCenterPoint_QNAME, ArcByCenterPointType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DirectPositionListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "posList")
    public JAXBElement<DirectPositionListType> createPosList(DirectPositionListType value) {
        return new JAXBElement<DirectPositionListType>(_PosList_QNAME, DirectPositionListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetaDataPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "metaDataProperty")
    public JAXBElement<MetaDataPropertyType> createMetaDataProperty(MetaDataPropertyType value) {
        return new JAXBElement<MetaDataPropertyType>(_MetaDataProperty_QNAME, MetaDataPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeIntervalLengthType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "timeInterval")
    public JAXBElement<TimeIntervalLengthType> createTimeInterval(TimeIntervalLengthType value) {
        return new JAXBElement<TimeIntervalLengthType>(_TimeInterval_QNAME, TimeIntervalLengthType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeOrNilReasonListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CategoryList", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractScalarValueList")
    public JAXBElement<CodeOrNilReasonListType> createCategoryList(CodeOrNilReasonListType value) {
        return new JAXBElement<CodeOrNilReasonListType>(_CategoryList_QNAME, CodeOrNilReasonListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "doubleOrNilReasonTupleList")
    public JAXBElement<List<String>> createDoubleOrNilReasonTupleList(List<String> value) {
        return new JAXBElement<List<String>>(_DoubleOrNilReasonTupleList_QNAME, ((Class) List.class), null, ((List<String> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTimeTopologyPrimitiveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractTimeTopologyPrimitive", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTimePrimitive")
    public JAXBElement<AbstractTimeTopologyPrimitiveType> createAbstractTimeTopologyPrimitive(AbstractTimeTopologyPrimitiveType value) {
        return new JAXBElement<AbstractTimeTopologyPrimitiveType>(_AbstractTimeTopologyPrimitive_QNAME, AbstractTimeTopologyPrimitiveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurveArrayPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "curveMembers")
    public JAXBElement<CurveArrayPropertyType> createCurveMembers(CurveArrayPropertyType value) {
        return new JAXBElement<CurveArrayPropertyType>(_CurveMembers_QNAME, CurveArrayPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "locationName")
    public JAXBElement<CodeType> createLocationName(CodeType value) {
        return new JAXBElement<CodeType>(_LocationName_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DirectPositionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "pos")
    public JAXBElement<DirectPositionType> createPos(DirectPositionType value) {
        return new JAXBElement<DirectPositionType>(_Pos_QNAME, DirectPositionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractObject")
    public JAXBElement<Object> createAbstractObject(Object value) {
        return new JAXBElement<Object>(_AbstractObject_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValuePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "valueProperty")
    public JAXBElement<ValuePropertyType> createValueProperty(ValuePropertyType value) {
        return new JAXBElement<ValuePropertyType>(_ValueProperty_QNAME, ValuePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractGriddedSurfaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractGriddedSurface", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractParametricCurveSurface")
    public JAXBElement<AbstractGriddedSurfaceType> createAbstractGriddedSurface(AbstractGriddedSurfaceType value) {
        return new JAXBElement<AbstractGriddedSurfaceType>(_AbstractGriddedSurface_QNAME, AbstractGriddedSurfaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractRingPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "exterior")
    public JAXBElement<AbstractRingPropertyType> createExterior(AbstractRingPropertyType value) {
        return new JAXBElement<AbstractRingPropertyType>(_Exterior_QNAME, AbstractRingPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractGeometricPrimitiveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractGeometricPrimitive", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometry")
    public JAXBElement<AbstractGeometricPrimitiveType> createAbstractGeometricPrimitive(AbstractGeometricPrimitiveType value) {
        return new JAXBElement<AbstractGeometricPrimitiveType>(_AbstractGeometricPrimitive_QNAME, AbstractGeometricPrimitiveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "defaultCodeSpace")
    public JAXBElement<String> createDefaultCodeSpace(String value) {
        return new JAXBElement<String>(_DefaultCodeSpace_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTopologyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractTopology", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGML")
    public JAXBElement<AbstractTopologyType> createAbstractTopology(AbstractTopologyType value) {
        return new JAXBElement<AbstractTopologyType>(_AbstractTopology_QNAME, AbstractTopologyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Null")
    public JAXBElement<List<String>> createNull(List<String> value) {
        return new JAXBElement<List<String>>(_Null_QNAME, ((Class) List.class), null, ((List<String> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeCoordinateSystemType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeCoordinateSystem", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "TimeReferenceSystem")
    public JAXBElement<TimeCoordinateSystemType> createTimeCoordinateSystem(TimeCoordinateSystemType value) {
        return new JAXBElement<TimeCoordinateSystemType>(_TimeCoordinateSystem_QNAME, TimeCoordinateSystemType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeometryPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "geometryMember")
    public JAXBElement<GeometryPropertyType> createGeometryMember(GeometryPropertyType value) {
        return new JAXBElement<GeometryPropertyType>(_GeometryMember_QNAME, GeometryPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoCurveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TopoCurve")
    public JAXBElement<TopoCurveType> createTopoCurve(TopoCurveType value) {
        return new JAXBElement<TopoCurveType>(_TopoCurve_QNAME, TopoCurveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeNodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeNode", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTimeTopologyPrimitive")
    public JAXBElement<TimeNodeType> createTimeNode(TimeNodeType value) {
        return new JAXBElement<TimeNodeType>(_TimeNode_QNAME, TimeNodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Curve", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurve")
    public JAXBElement<CurveType> createCurve(CurveType value) {
        return new JAXBElement<CurveType>(_Curve_QNAME, CurveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimePeriodType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimePeriod", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTimeGeometricPrimitive")
    public JAXBElement<TimePeriodType> createTimePeriod(TimePeriodType value) {
        return new JAXBElement<TimePeriodType>(_TimePeriod_QNAME, TimePeriodType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractCurveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractCurve", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometricPrimitive")
    public JAXBElement<AbstractCurveType> createAbstractCurve(AbstractCurveType value) {
        return new JAXBElement<AbstractCurveType>(_AbstractCurve_QNAME, AbstractCurveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractValue", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<Object> createAbstractValue(Object value) {
        return new JAXBElement<Object>(_AbstractValue_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClothoidType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Clothoid", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurveSegment")
    public JAXBElement<ClothoidType> createClothoid(ClothoidType value) {
        return new JAXBElement<ClothoidType>(_Clothoid_QNAME, ClothoidType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "reversePropertyName")
    public JAXBElement<String> createReversePropertyName(String value) {
        return new JAXBElement<String>(_ReversePropertyName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "resultOf")
    public JAXBElement<ResultType> createResultOf(ResultType value) {
        return new JAXBElement<ResultType>(_ResultOf_QNAME, ResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTimeSliceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractTimeSlice", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGML")
    public JAXBElement<AbstractTimeSliceType> createAbstractTimeSlice(AbstractTimeSliceType value) {
        return new JAXBElement<AbstractTimeSliceType>(_AbstractTimeSlice_QNAME, AbstractTimeSliceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CubicSplineType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CubicSpline", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurveSegment")
    public JAXBElement<CubicSplineType> createCubicSpline(CubicSplineType value) {
        return new JAXBElement<CubicSplineType>(_CubicSpline_QNAME, CubicSplineType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscreteCoverageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractDiscreteCoverage", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCoverage")
    public JAXBElement<DiscreteCoverageType> createAbstractDiscreteCoverage(DiscreteCoverageType value) {
        return new JAXBElement<DiscreteCoverageType>(_AbstractDiscreteCoverage_QNAME, DiscreteCoverageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TinType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Tin", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "TriangulatedSurface")
    public JAXBElement<TinType> createTin(TinType value) {
        return new JAXBElement<TinType>(_Tin_QNAME, TinType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Face", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTopoPrimitive")
    public JAXBElement<FaceType> createFace(FaceType value) {
        return new JAXBElement<FaceType>(_Face_QNAME, FaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoCurvePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "topoCurveProperty")
    public JAXBElement<TopoCurvePropertyType> createTopoCurveProperty(TopoCurvePropertyType value) {
        return new JAXBElement<TopoCurvePropertyType>(_TopoCurveProperty_QNAME, TopoCurvePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeOrdinalEraType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeOrdinalEra")
    public JAXBElement<TimeOrdinalEraType> createTimeOrdinalEra(TimeOrdinalEraType value) {
        return new JAXBElement<TimeOrdinalEraType>(_TimeOrdinalEra_QNAME, TimeOrdinalEraType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DomainSetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "domainSet")
    public JAXBElement<DomainSetType> createDomainSet(DomainSetType value) {
        return new JAXBElement<DomainSetType>(_DomainSet_QNAME, DomainSetType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiSurfaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "MultiSurface", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometricAggregate")
    public JAXBElement<MultiSurfaceType> createMultiSurface(MultiSurfaceType value) {
        return new JAXBElement<MultiSurfaceType>(_MultiSurface_QNAME, MultiSurfaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AngleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "angle")
    public JAXBElement<AngleType> createAngle(AngleType value) {
        return new JAXBElement<AngleType>(_Angle_QNAME, AngleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoPrimitiveArrayAssociationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "topoPrimitiveMembers")
    public JAXBElement<TopoPrimitiveArrayAssociationType> createTopoPrimitiveMembers(TopoPrimitiveArrayAssociationType value) {
        return new JAXBElement<TopoPrimitiveArrayAssociationType>(_TopoPrimitiveMembers_QNAME, TopoPrimitiveArrayAssociationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurvePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "curveMember")
    public JAXBElement<CurvePropertyType> createCurveMember(CurvePropertyType value) {
        return new JAXBElement<CurvePropertyType>(_CurveMember_QNAME, CurvePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeCalendarType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeCalendar", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "TimeReferenceSystem")
    public JAXBElement<TimeCalendarType> createTimeCalendar(TimeCalendarType value) {
        return new JAXBElement<TimeCalendarType>(_TimeCalendar_QNAME, TimeCalendarType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RectifiedGridType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "RectifiedGrid", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "Grid")
    public JAXBElement<RectifiedGridType> createRectifiedGrid(RectifiedGridType value) {
        return new JAXBElement<RectifiedGridType>(_RectifiedGrid_QNAME, RectifiedGridType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTimeObjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractTimeObject", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGML")
    public JAXBElement<AbstractTimeObjectType> createAbstractTimeObject(AbstractTimeObjectType value) {
        return new JAXBElement<AbstractTimeObjectType>(_AbstractTimeObject_QNAME, AbstractTimeObjectType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscreteCoverageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "RectifiedGridCoverage", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractDiscreteCoverage")
    public JAXBElement<DiscreteCoverageType> createRectifiedGridCoverage(DiscreteCoverageType value) {
        return new JAXBElement<DiscreteCoverageType>(_RectifiedGridCoverage_QNAME, DiscreteCoverageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractRingPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "interior")
    public JAXBElement<AbstractRingPropertyType> createInterior(AbstractRingPropertyType value) {
        return new JAXBElement<AbstractRingPropertyType>(_Interior_QNAME, AbstractRingPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocationPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "location")
    public JAXBElement<LocationPropertyType> createLocation(LocationPropertyType value) {
        return new JAXBElement<LocationPropertyType>(_Location_QNAME, LocationPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoComplexPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "subComplex")
    public JAXBElement<TopoComplexPropertyType> createSubComplex(TopoComplexPropertyType value) {
        return new JAXBElement<TopoComplexPropertyType>(_SubComplex_QNAME, TopoComplexPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoPointPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "topoPointProperty")
    public JAXBElement<TopoPointPropertyType> createTopoPointProperty(TopoPointPropertyType value) {
        return new JAXBElement<TopoPointPropertyType>(_TopoPointProperty_QNAME, TopoPointPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CoordinatesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "tupleList")
    public JAXBElement<CoordinatesType> createTupleList(CoordinatesType value) {
        return new JAXBElement<CoordinatesType>(_TupleList_QNAME, CoordinatesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractScalarValue", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractValue")
    public JAXBElement<Object> createAbstractScalarValue(Object value) {
        return new JAXBElement<Object>(_AbstractScalarValue_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiCurveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "MultiCurve", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometricAggregate")
    public JAXBElement<MultiCurveType> createMultiCurve(MultiCurveType value) {
        return new JAXBElement<MultiCurveType>(_MultiCurve_QNAME, MultiCurveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeCalendarEraType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeCalendarEra")
    public JAXBElement<TimeCalendarEraType> createTimeCalendarEra(TimeCalendarEraType value) {
        return new JAXBElement<TimeCalendarEraType>(_TimeCalendarEra_QNAME, TimeCalendarEraType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OffsetCurveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "OffsetCurve", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurveSegment")
    public JAXBElement<OffsetCurveType> createOffsetCurve(OffsetCurveType value) {
        return new JAXBElement<OffsetCurveType>(_OffsetCurve_QNAME, OffsetCurveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConversionToPreferredUnitType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "conversionToPreferredUnit")
    public JAXBElement<ConversionToPreferredUnitType> createConversionToPreferredUnit(ConversionToPreferredUnitType value) {
        return new JAXBElement<ConversionToPreferredUnitType>(_ConversionToPreferredUnit_QNAME, ConversionToPreferredUnitType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractContinuousCoverageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractContinuousCoverage", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractFeature")
    public JAXBElement<AbstractContinuousCoverageType> createAbstractContinuousCoverage(AbstractContinuousCoverageType value) {
        return new JAXBElement<AbstractContinuousCoverageType>(_AbstractContinuousCoverage_QNAME, AbstractContinuousCoverageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GridFunctionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "GridFunction", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<GridFunctionType> createGridFunction(GridFunctionType value) {
        return new JAXBElement<GridFunctionType>(_GridFunction_QNAME, GridFunctionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PointPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "centerOf")
    public JAXBElement<PointPropertyType> createCenterOf(PointPropertyType value) {
        return new JAXBElement<PointPropertyType>(_CenterOf_QNAME, PointPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DirectedFacePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "directedFace")
    public JAXBElement<DirectedFacePropertyType> createDirectedFace(DirectedFacePropertyType value) {
        return new JAXBElement<DirectedFacePropertyType>(_DirectedFace_QNAME, DirectedFacePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "gmlProfileSchema")
    public JAXBElement<String> createGmlProfileSchema(String value) {
        return new JAXBElement<String>(_GmlProfileSchema_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TriangleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Triangle", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractSurfacePatch")
    public JAXBElement<TriangleType> createTriangle(TriangleType value) {
        return new JAXBElement<TriangleType>(_Triangle_QNAME, TriangleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoSolidType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TopoSolid", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTopoPrimitive")
    public JAXBElement<TopoSolidType> createTopoSolid(TopoSolidType value) {
        return new JAXBElement<TopoSolidType>(_TopoSolid_QNAME, TopoSolidType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociationRoleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "abstractAssociationRole")
    public JAXBElement<AssociationRoleType> createAbstractAssociationRole(AssociationRoleType value) {
        return new JAXBElement<AssociationRoleType>(_AbstractAssociationRole_QNAME, AssociationRoleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractFeatureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractFeature", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGML")
    public JAXBElement<AbstractFeatureType> createAbstractFeature(AbstractFeatureType value) {
        return new JAXBElement<AbstractFeatureType>(_AbstractFeature_QNAME, AbstractFeatureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeClockType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeClock", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "TimeReferenceSystem")
    public JAXBElement<TimeClockType> createTimeClock(TimeClockType value) {
        return new JAXBElement<TimeClockType>(_TimeClock_QNAME, TimeClockType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTopoPrimitiveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractTopoPrimitive", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTopology")
    public JAXBElement<AbstractTopoPrimitiveType> createAbstractTopoPrimitive(AbstractTopoPrimitiveType value) {
        return new JAXBElement<AbstractTopoPrimitiveType>(_AbstractTopoPrimitive_QNAME, AbstractTopoPrimitiveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BezierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Bezier", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "BSpline")
    public JAXBElement<BezierType> createBezier(BezierType value) {
        return new JAXBElement<BezierType>(_Bezier_QNAME, BezierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DirectedObservationAtDistanceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "DirectedObservationAtDistance", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "DirectedObservation")
    public JAXBElement<DirectedObservationAtDistanceType> createDirectedObservationAtDistance(DirectedObservationAtDistanceType value) {
        return new JAXBElement<DirectedObservationAtDistanceType>(_DirectedObservationAtDistance_QNAME, DirectedObservationAtDistanceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SurfacePatchArrayPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "patches")
    public JAXBElement<SurfacePatchArrayPropertyType> createPatches(SurfacePatchArrayPropertyType value) {
        return new JAXBElement<SurfacePatchArrayPropertyType>(_Patches_QNAME, SurfacePatchArrayPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeometryArrayPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "geometryMembers")
    public JAXBElement<GeometryArrayPropertyType> createGeometryMembers(GeometryArrayPropertyType value) {
        return new JAXBElement<GeometryArrayPropertyType>(_GeometryMembers_QNAME, GeometryArrayPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DictionaryEntryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "dictionaryEntry")
    public JAXBElement<DictionaryEntryType> createDictionaryEntry(DictionaryEntryType value) {
        return new JAXBElement<DictionaryEntryType>(_DictionaryEntry_QNAME, DictionaryEntryType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "LocationKeyWord")
    public JAXBElement<CodeType> createLocationKeyWord(CodeType value) {
        return new JAXBElement<CodeType>(_LocationKeyWord_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractGeometryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractGeometry", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGML")
    public JAXBElement<AbstractGeometryType> createAbstractGeometry(AbstractGeometryType value) {
        return new JAXBElement<AbstractGeometryType>(_AbstractGeometry_QNAME, AbstractGeometryType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeInstantType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TimeInstant", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTimeGeometricPrimitive")
    public JAXBElement<TimeInstantType> createTimeInstant(TimeInstantType value) {
        return new JAXBElement<TimeInstantType>(_TimeInstant_QNAME, TimeInstantType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GridType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Grid", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractImplicitGeometry")
    public JAXBElement<GridType> createGrid(GridType value) {
        return new JAXBElement<GridType>(_Grid_QNAME, GridType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTimeGeometricPrimitiveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractTimeGeometricPrimitive", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTimePrimitive")
    public JAXBElement<AbstractTimeGeometricPrimitiveType> createAbstractTimeGeometricPrimitive(AbstractTimeGeometricPrimitiveType value) {
        return new JAXBElement<AbstractTimeGeometricPrimitiveType>(_AbstractTimeGeometricPrimitive_QNAME, AbstractTimeGeometricPrimitiveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CylinderType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Cylinder", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGriddedSurface")
    public JAXBElement<CylinderType> createCylinder(CylinderType value) {
        return new JAXBElement<CylinderType>(_Cylinder_QNAME, CylinderType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurveSegmentArrayPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "segments")
    public JAXBElement<CurveSegmentArrayPropertyType> createSegments(CurveSegmentArrayPropertyType value) {
        return new JAXBElement<CurveSegmentArrayPropertyType>(_Segments_QNAME, CurveSegmentArrayPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TargetPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "subject", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "target")
    public JAXBElement<TargetPropertyType> createSubject(TargetPropertyType value) {
        return new JAXBElement<TargetPropertyType>(_Subject_QNAME, TargetPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeodesicStringType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "GeodesicString", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurveSegment")
    public JAXBElement<GeodesicStringType> createGeodesicString(GeodesicStringType value) {
        return new JAXBElement<GeodesicStringType>(_GeodesicString_QNAME, GeodesicStringType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DefinitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Definition", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGML")
    public JAXBElement<DefinitionType> createDefinition(DefinitionType value) {
        return new JAXBElement<DefinitionType>(_Definition_QNAME, DefinitionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValueArrayPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "valueComponents")
    public JAXBElement<ValueArrayPropertyType> createValueComponents(ValueArrayPropertyType value) {
        return new JAXBElement<ValueArrayPropertyType>(_ValueComponents_QNAME, ValueArrayPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringOrRefType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "LocationString")
    public JAXBElement<StringOrRefType> createLocationString(StringOrRefType value) {
        return new JAXBElement<StringOrRefType>(_LocationString_QNAME, StringOrRefType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractGeometricAggregateType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractGeometricAggregate", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometry")
    public JAXBElement<AbstractGeometricAggregateType> createAbstractGeometricAggregate(AbstractGeometricAggregateType value) {
        return new JAXBElement<AbstractGeometricAggregateType>(_AbstractGeometricAggregate_QNAME, AbstractGeometricAggregateType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolidArrayPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "solidMembers")
    public JAXBElement<SolidArrayPropertyType> createSolidMembers(SolidArrayPropertyType value) {
        return new JAXBElement<SolidArrayPropertyType>(_SolidMembers_QNAME, SolidArrayPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CircleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Circle", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "Arc")
    public JAXBElement<CircleType> createCircle(CircleType value) {
        return new JAXBElement<CircleType>(_Circle_QNAME, CircleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurvePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "curveProperty")
    public JAXBElement<CurvePropertyType> createCurveProperty(CurvePropertyType value) {
        return new JAXBElement<CurvePropertyType>(_CurveProperty_QNAME, CurvePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConventionalUnitType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "ConventionalUnit", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "UnitDefinition")
    public JAXBElement<ConventionalUnitType> createConventionalUnit(ConventionalUnitType value) {
        return new JAXBElement<ConventionalUnitType>(_ConventionalUnit_QNAME, ConventionalUnitType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SurfaceArrayPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "surfaceMembers")
    public JAXBElement<SurfaceArrayPropertyType> createSurfaceMembers(SurfaceArrayPropertyType value) {
        return new JAXBElement<SurfaceArrayPropertyType>(_SurfaceMembers_QNAME, SurfaceArrayPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeometricComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "GeometricComplex", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometry")
    public JAXBElement<GeometricComplexType> createGeometricComplex(GeometricComplexType value) {
        return new JAXBElement<GeometricComplexType>(_GeometricComplex_QNAME, GeometricComplexType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SurfaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "TriangulatedSurface", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "Surface")
    public JAXBElement<SurfaceType> createTriangulatedSurface(SurfaceType value) {
        return new JAXBElement<SurfaceType>(_TriangulatedSurface_QNAME, SurfaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuantityExtentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "QuantityExtent", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractValue")
    public JAXBElement<QuantityExtentType> createQuantityExtent(QuantityExtentType value) {
        return new JAXBElement<QuantityExtentType>(_QuantityExtent_QNAME, QuantityExtentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LineStringSegmentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "LineStringSegment", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractCurveSegment")
    public JAXBElement<LineStringSegmentType> createLineStringSegment(LineStringSegmentType value) {
        return new JAXBElement<LineStringSegmentType>(_LineStringSegment_QNAME, LineStringSegmentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompositeSurfaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CompositeSurface", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractSurface")
    public JAXBElement<CompositeSurfaceType> createCompositeSurface(CompositeSurfaceType value) {
        return new JAXBElement<CompositeSurfaceType>(_CompositeSurface_QNAME, CompositeSurfaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolidPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "solidProperty")
    public JAXBElement<SolidPropertyType> createSolidProperty(SolidPropertyType value) {
        return new JAXBElement<SolidPropertyType>(_SolidProperty_QNAME, SolidPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscreteCoverageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "GridCoverage", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractDiscreteCoverage")
    public JAXBElement<DiscreteCoverageType> createGridCoverage(DiscreteCoverageType value) {
        return new JAXBElement<DiscreteCoverageType>(_GridCoverage_QNAME, DiscreteCoverageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimePrimitivePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "validTime")
    public JAXBElement<TimePrimitivePropertyType> createValidTime(TimePrimitivePropertyType value) {
        return new JAXBElement<TimePrimitivePropertyType>(_ValidTime_QNAME, TimePrimitivePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnitDefinitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "UnitDefinition", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "Definition")
    public JAXBElement<UnitDefinitionType> createUnitDefinition(UnitDefinitionType value) {
        return new JAXBElement<UnitDefinitionType>(_UnitDefinition_QNAME, UnitDefinitionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoComplexPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "superComplex")
    public JAXBElement<TopoComplexPropertyType> createSuperComplex(TopoComplexPropertyType value) {
        return new JAXBElement<TopoComplexPropertyType>(_SuperComplex_QNAME, TopoComplexPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CoverageFunctionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "coverageFunction", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<CoverageFunctionType> createCoverageFunction(CoverageFunctionType value) {
        return new JAXBElement<CoverageFunctionType>(_CoverageFunction_QNAME, CoverageFunctionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscreteCoverageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "MultiSolidCoverage", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractDiscreteCoverage")
    public JAXBElement<DiscreteCoverageType> createMultiSolidCoverage(DiscreteCoverageType value) {
        return new JAXBElement<DiscreteCoverageType>(_MultiSolidCoverage_QNAME, DiscreteCoverageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RangeSetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "rangeSet")
    public JAXBElement<RangeSetType> createRangeSet(RangeSetType value) {
        return new JAXBElement<RangeSetType>(_RangeSet_QNAME, RangeSetType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SurfaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "PolyhedralSurface", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "Surface")
    public JAXBElement<SurfaceType> createPolyhedralSurface(SurfaceType value) {
        return new JAXBElement<SurfaceType>(_PolyhedralSurface_QNAME, SurfaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "name")
    public JAXBElement<CodeType> createName(CodeType value) {
        return new JAXBElement<CodeType>(_Name_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "remarks")
    public JAXBElement<String> createRemarks(String value) {
        return new JAXBElement<String>(_Remarks_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CategoryExtentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CategoryExtent", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractValue")
    public JAXBElement<CategoryExtentType> createCategoryExtent(CategoryExtentType value) {
        return new JAXBElement<CategoryExtentType>(_CategoryExtent_QNAME, CategoryExtentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociationRoleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "rangeParameters")
    public JAXBElement<AssociationRoleType> createRangeParameters(AssociationRoleType value) {
        return new JAXBElement<AssociationRoleType>(_RangeParameters_QNAME, AssociationRoleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MeasureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "measure")
    public JAXBElement<MeasureType> createMeasure(MeasureType value) {
        return new JAXBElement<MeasureType>(_Measure_QNAME, MeasureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MeasureOrNilReasonListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "QuantityList", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractScalarValueList")
    public JAXBElement<MeasureOrNilReasonListType> createQuantityList(MeasureOrNilReasonListType value) {
        return new JAXBElement<MeasureOrNilReasonListType>(_QuantityList_QNAME, MeasureOrNilReasonListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CountList", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractScalarValueList")
    public JAXBElement<List<String>> createCountList(List<String> value) {
        return new JAXBElement<List<String>>(_CountList_QNAME, ((Class) List.class), null, ((List<String> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PolygonPatchType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "PolygonPatch", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractSurfacePatch")
    public JAXBElement<PolygonPatchType> createPolygonPatch(PolygonPatchType value) {
        return new JAXBElement<PolygonPatchType>(_PolygonPatch_QNAME, PolygonPatchType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Boolean", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractScalarValue")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractSolidType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractSolid", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometricPrimitive")
    public JAXBElement<AbstractSolidType> createAbstractSolid(AbstractSolidType value) {
        return new JAXBElement<AbstractSolidType>(_AbstractSolid_QNAME, AbstractSolidType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AffinePlacementType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AffinePlacement", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<AffinePlacementType> createAffinePlacement(AffinePlacementType value) {
        return new JAXBElement<AffinePlacementType>(_AffinePlacement_QNAME, AffinePlacementType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SurfacePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "surfaceProperty")
    public JAXBElement<SurfacePropertyType> createSurfaceProperty(SurfacePropertyType value) {
        return new JAXBElement<SurfacePropertyType>(_SurfaceProperty_QNAME, SurfacePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractCurveSegmentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractCurveSegment", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<AbstractCurveSegmentType> createAbstractCurveSegment(AbstractCurveSegmentType value) {
        return new JAXBElement<AbstractCurveSegmentType>(_AbstractCurveSegment_QNAME, AbstractCurveSegmentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractGeometryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractImplicitGeometry", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometry")
    public JAXBElement<AbstractGeometryType> createAbstractImplicitGeometry(AbstractGeometryType value) {
        return new JAXBElement<AbstractGeometryType>(_AbstractImplicitGeometry_QNAME, AbstractGeometryType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SurfaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Surface", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractSurface")
    public JAXBElement<SurfaceType> createSurface(SurfaceType value) {
        return new JAXBElement<SurfaceType>(_Surface_QNAME, SurfaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociationRoleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "abstractStrictAssociationRole")
    public JAXBElement<AssociationRoleType> createAbstractStrictAssociationRole(AssociationRoleType value) {
        return new JAXBElement<AssociationRoleType>(_AbstractStrictAssociationRole_QNAME, AssociationRoleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShellType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Shell", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractObject")
    public JAXBElement<ShellType> createShell(ShellType value) {
        return new JAXBElement<ShellType>(_Shell_QNAME, ShellType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTimeComplexType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractTimeComplex", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTimeObject")
    public JAXBElement<AbstractTimeComplexType> createAbstractTimeComplex(AbstractTimeComplexType value) {
        return new JAXBElement<AbstractTimeComplexType>(_AbstractTimeComplex_QNAME, AbstractTimeComplexType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PointType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Point", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractGeometricPrimitive")
    public JAXBElement<PointType> createPoint(PointType value) {
        return new JAXBElement<PointType>(_Point_QNAME, PointType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractCoverageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "AbstractCoverage", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractFeature")
    public JAXBElement<AbstractCoverageType> createAbstractCoverage(AbstractCoverageType value) {
        return new JAXBElement<AbstractCoverageType>(_AbstractCoverage_QNAME, AbstractCoverageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcedurePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "using")
    public JAXBElement<ProcedurePropertyType> createUsing(ProcedurePropertyType value) {
        return new JAXBElement<ProcedurePropertyType>(_Using_QNAME, ProcedurePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArcType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Arc", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "ArcString")
    public JAXBElement<ArcType> createArc(ArcType value) {
        return new JAXBElement<ArcType>(_Arc_QNAME, ArcType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DynamicFeatureMemberType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "dynamicMembers")
    public JAXBElement<DynamicFeatureMemberType> createDynamicMembers(DynamicFeatureMemberType value) {
        return new JAXBElement<DynamicFeatureMemberType>(_DynamicMembers_QNAME, DynamicFeatureMemberType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PointPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "pointProperty")
    public JAXBElement<PointPropertyType> createPointProperty(PointPropertyType value) {
        return new JAXBElement<PointPropertyType>(_PointProperty_QNAME, PointPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Node", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTopoPrimitive")
    public JAXBElement<NodeType> createNode(NodeType value) {
        return new JAXBElement<NodeType>(_Node_QNAME, NodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InlinePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "abstractInlineProperty")
    public JAXBElement<InlinePropertyType> createAbstractInlineProperty(InlinePropertyType value) {
        return new JAXBElement<InlinePropertyType>(_AbstractInlineProperty_QNAME, InlinePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EdgeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Edge", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractTopoPrimitive")
    public JAXBElement<EdgeType> createEdge(EdgeType value) {
        return new JAXBElement<EdgeType>(_Edge_QNAME, EdgeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValuePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "valueComponent")
    public JAXBElement<ValuePropertyType> createValueComponent(ValuePropertyType value) {
        return new JAXBElement<ValuePropertyType>(_ValueComponent_QNAME, ValuePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConversionToPreferredUnitType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "roughConversionToPreferredUnit")
    public JAXBElement<ConversionToPreferredUnitType> createRoughConversionToPreferredUnit(ConversionToPreferredUnitType value) {
        return new JAXBElement<ConversionToPreferredUnitType>(_RoughConversionToPreferredUnit_QNAME, ConversionToPreferredUnitType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "catalogSymbol")
    public JAXBElement<CodeType> createCatalogSymbol(CodeType value) {
        return new JAXBElement<CodeType>(_CatalogSymbol_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DerivedUnitType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "DerivedUnit", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "UnitDefinition")
    public JAXBElement<DerivedUnitType> createDerivedUnit(DerivedUnitType value) {
        return new JAXBElement<DerivedUnitType>(_DerivedUnit_QNAME, DerivedUnitType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SurfacePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "extentOf")
    public JAXBElement<SurfacePropertyType> createExtentOf(SurfacePropertyType value) {
        return new JAXBElement<SurfacePropertyType>(_ExtentOf_QNAME, SurfacePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "CountExtent", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractValue")
    public JAXBElement<List<String>> createCountExtent(List<String> value) {
        return new JAXBElement<List<String>>(_CountExtent_QNAME, ((Class) List.class), null, ((List<String> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DirectedEdgePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "directedEdge")
    public JAXBElement<DirectedEdgePropertyType> createDirectedEdge(DirectedEdgePropertyType value) {
        return new JAXBElement<DirectedEdgePropertyType>(_DirectedEdge_QNAME, DirectedEdgePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RingType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Ring", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractRing")
    public JAXBElement<RingType> createRing(RingType value) {
        return new JAXBElement<RingType>(_Ring_QNAME, RingType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopoVolumePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "topoVolumeProperty")
    public JAXBElement<TopoVolumePropertyType> createTopoVolumeProperty(TopoVolumePropertyType value) {
        return new JAXBElement<TopoVolumePropertyType>(_TopoVolumeProperty_QNAME, TopoVolumePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RectangleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Rectangle", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractSurfacePatch")
    public JAXBElement<RectangleType> createRectangle(RectangleType value) {
        return new JAXBElement<RectangleType>(_Rectangle_QNAME, RectangleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DerivationUnitTermType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "derivationUnitTerm")
    public JAXBElement<DerivationUnitTermType> createDerivationUnitTerm(DerivationUnitTermType value) {
        return new JAXBElement<DerivationUnitTermType>(_DerivationUnitTerm_QNAME, DerivationUnitTermType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HistoryPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "history")
    public JAXBElement<HistoryPropertyType> createHistory(HistoryPropertyType value) {
        return new JAXBElement<HistoryPropertyType>(_History_QNAME, HistoryPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PolygonType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Polygon", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractSurface")
    public JAXBElement<PolygonType> createPolygon(PolygonType value) {
        return new JAXBElement<PolygonType>(_Polygon_QNAME, PolygonType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeodesicType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "Geodesic", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "GeodesicString")
    public JAXBElement<GeodesicType> createGeodesic(GeodesicType value) {
        return new JAXBElement<GeodesicType>(_Geodesic_QNAME, GeodesicType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolidPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "solidMember")
    public JAXBElement<SolidPropertyType> createSolidMember(SolidPropertyType value) {
        return new JAXBElement<SolidPropertyType>(_SolidMember_QNAME, SolidPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrientableSurfaceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "OrientableSurface", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractSurface")
    public JAXBElement<OrientableSurfaceType> createOrientableSurface(OrientableSurfaceType value) {
        return new JAXBElement<OrientableSurfaceType>(_OrientableSurface_QNAME, OrientableSurfaceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "BooleanList", substitutionHeadNamespace = "http://www.opengis.net/gml/3.2", substitutionHeadName = "AbstractScalarValueList")
    public JAXBElement<List<String>> createBooleanList(List<String> value) {
        return new JAXBElement<List<String>>(_BooleanList_QNAME, ((Class) List.class), null, ((List<String> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GridLimitsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "limits", scope = GridType.class)
    public JAXBElement<GridLimitsType> createGridTypeLimits(GridLimitsType value) {
        return new JAXBElement<GridLimitsType>(_GridTypeLimits_QNAME, GridLimitsType.class, GridType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "axisName", scope = GridType.class)
    public JAXBElement<String> createGridTypeAxisName(String value) {
        return new JAXBElement<String>(_GridTypeAxisName_QNAME, String.class, GridType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/gml/3.2", name = "axisLabels", scope = GridType.class)
    public JAXBElement<List<String>> createGridTypeAxisLabels(List<String> value) {
        return new JAXBElement<List<String>>(_GridTypeAxisLabels_QNAME, ((Class) List.class), GridType.class, ((List<String> ) value));
    }

}
