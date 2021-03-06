/*******************************************************************************
 * Copyright (c) 2010, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ui.debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.FileURIHandlerImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.IWorkspaceCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.gef.ui.figures.SlidableAnchor;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.internal.movida.ViewpointSelection;
import org.eclipse.sirius.business.internal.movida.registry.ViewpointRegistry;
import org.eclipse.sirius.business.internal.movida.registry.ViewpointRegistryListener;
import org.eclipse.sirius.diagram.AbsoluteBoundsFilter;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.diagram.sequence.SequenceDDiagram;
import org.eclipse.sirius.diagram.sequence.business.internal.VerticalPositionFunction;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceElement;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceElementAccessor;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceEvent;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Lifeline;
import org.eclipse.sirius.diagram.sequence.business.internal.operation.VerticalSpaceExpansion;
import org.eclipse.sirius.diagram.sequence.ordering.EventEnd;
import org.eclipse.sirius.diagram.sequence.ui.tool.api.SequenceDiagramLayout;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.ISequenceEventEditPart;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.InstanceRoleEditPart;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.LifelineEditPart;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.SequenceDiagramEditPart;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.SequenceMessageEditPart;
import org.eclipse.sirius.diagram.sequence.util.Range;
import org.eclipse.sirius.diagram.ui.business.internal.query.EdgeTargetQuery;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramNameEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.IAbstractDiagramNodeEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.IDiagramEdgeEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.IDiagramElementEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DDiagramEditPart;
import org.eclipse.sirius.diagram.ui.internal.refresh.GMFHelper;
import org.eclipse.sirius.diagram.ui.tools.internal.commands.ToggleFoldingStateCommand;
import org.eclipse.sirius.diagram.ui.tools.internal.edit.command.CommandFactory;
import org.eclipse.sirius.editor.tools.internal.presentation.ViewpoitnDependenciesSelectionDialog;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.emf.AllContents;
import org.eclipse.sirius.tests.sample.component.Component;
import org.eclipse.sirius.tests.sample.component.util.PayloadMarkerAdapter;
import org.eclipse.sirius.tests.sample.component.util.PayloadMarkerAdapter.FeatureAccess;
import org.eclipse.sirius.ui.business.api.viewpoint.ViewpointSelectionDialog;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.team.internal.core.streams.ProgressMonitorInputStream;

import com.google.common.base.Functions;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;

/**
 * A simple view synchronized with the Eclipse selection, used to show arbitrary
 * computed information from the currently selected edit part of a diagram and
 * launch actions on them. This view is targeted to developers to help
 * debugging, it should not be packaged in the final product and should not be
 * available to end users.
 * 
 * @author pcdavid
 */
@SuppressWarnings({ "restriction", "unused" })
public class SiriusDebugView extends AbstractDebugView {
    /**
     * The global ID for the Eclipse View.
     */
    public static final String ID = "org.eclipse.sirius.ui.debug.DebugView";

    /**
     * Memento used by the Store Positions/Show position changes actions.
     */
    protected Map<EObject, Integer> storedPositions;

    protected ViewpointRegistry registry;

    /**
     * Returns the text to show in the main text area for the specified object.
     */
    @Override
    protected String getTextFor(Object obj) {
        if (obj instanceof IDiagramElementEditPart) {
            return getTextFor((IDiagramElementEditPart) obj);
        } else if (obj instanceof DDiagramEditPart) {
            return getTextForDiagram((DDiagramEditPart) obj);
        } else if (obj instanceof ConnectionEditPart) {
            return getTextForConnection((ConnectionEditPart) obj);
        } else {
            return "Selection type not supported: " + obj;
        }
    }

    private String getTextForDiagram(DDiagramEditPart sdep) {
        StringBuilder sb = new StringBuilder();
        sb.append("Direct diagram children:\n");
        for (IGraphicalEditPart child : Iterables.filter(sdep.getChildren(), IGraphicalEditPart.class)) {
            sb.append("- " + toString(child));
            sb.append("\n  - sources: ");
            for (IGraphicalEditPart sourceConn : Iterables.filter(child.getSourceConnections(), IGraphicalEditPart.class)) {
                sb.append("\n      ").append(toString(sourceConn));
            }
            sb.append("\n  - targets: ");
            for (IGraphicalEditPart targetConn : Iterables.filter(child.getTargetConnections(), IGraphicalEditPart.class)) {
                sb.append("\n      ").append(toString(targetConn));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String toString(IGraphicalEditPart part) {
        return String.valueOf(part).replace("org.eclipse.gmf.runtime.notation.impl.", "") + " #" + Long.toHexString(part.hashCode());
    }

    private String getTextFor(IDiagramElementEditPart part) {
        StringBuilder sb = new StringBuilder();
        if (part instanceof ShapeEditPart) {
            appendSequenceEventInfo(part, sb);
            appendBoundsDetails(part, sb);

            if (part instanceof InstanceRoleEditPart) {
                LifelineEditPart lep = Iterables.filter(part.getChildren(), LifelineEditPart.class).iterator().next();
                appendSequenceEventInfo(lep, sb);
            }
        } else if (part instanceof ConnectionEditPart) {
            ConnectionEditPart conn = (ConnectionEditPart) part;
            sb.append(getTextForConnection(conn));
        } else if (part instanceof AbstractDiagramNameEditPart) {
            appendBoundsDetails(part, sb);
        } else {
            sb.append("unknown");
        }
        return sb.toString();
    }

    private void appendBoundsDetails(IDiagramElementEditPart part, StringBuilder sb) {
        LayoutConstraint layoutConstraint = ((Node) ((IGraphicalEditPart) part).getNotationView()).getLayoutConstraint();
        if (layoutConstraint instanceof Bounds) {
            Bounds bounds = (Bounds) layoutConstraint;
            sb.append("Bounds (GMF):              Rectangle(" + bounds.getX() + ", " + bounds.getY() + ", " + bounds.getWidth() + ", " + bounds.getHeight() + ")\n");
        } else if (layoutConstraint instanceof Location) {
            Location location = (Location) layoutConstraint;
            sb.append("Location (GMF):         Location(" + location.getX() + ", " + location.getY() + ")\n");
        }
        Option<ISequenceElement> elt = ISequenceElementAccessor.getISequenceElement(part.getNotationView());
        if (elt.some()) {
            sb.append("Bounds (logical):          " + elt.get().getProperLogicalBounds()).append("\n");
        }
        Rectangle bounds = ((IGraphicalEditPart) part).getFigure().getBounds().getCopy();
        sb.append("Bounds (Draw2D):           " + bounds.toString() + "\n");
        ((IGraphicalEditPart) part).getFigure().translateToAbsolute(bounds);
        sb.append("Bounds (Draw2D absolute):  " + bounds.toString() + "\n");
        sb.append("\n");
    }

    private String getTextForConnection(ConnectionEditPart conn) {
        Edge edge = (Edge) conn.getNotationView();
        StringBuilder sb = new StringBuilder();

        if (conn instanceof IDiagramElementEditPart) {
            appendSequenceEventInfo((IDiagramElementEditPart) conn, sb);
        }

        if (conn instanceof IDiagramEdgeEditPart) {
            PolylineConnectionEx polyline = ((IDiagramEdgeEditPart) conn).getPolylineConnectionFigure();
            sb.append("Line width = " + polyline.getLineWidth() + " (float: " + polyline.getLineWidthFloat() + ")\n");
        }

        sb.append("GMF Notation (Edge):\n");

        Point srcAnchorLoc = appendAnchorDetails(conn, (IdentityAnchor) edge.getSourceAnchor(), ((Connection) conn.getFigure()).getSourceAnchor(), "  . sourceAnchor", sb);
        Point tgtAnchorLoc = appendAnchorDetails(conn, (IdentityAnchor) edge.getTargetAnchor(), ((Connection) conn.getFigure()).getTargetAnchor(), "  . targetAnchor", sb);
        appendRelativeBenpointsDetails(edge, conn, sb);

        sb.append("\nDraw2D Connection:\n");
        Connection connFigure = conn.getConnectionFigure();

        sb.append("  . routing constraint:\n");
        List<Bendpoint> bendpoints = (List<Bendpoint>) connFigure.getRoutingConstraint();
        if (bendpoints != null) {
            for (int i = 0; i < bendpoints.size(); i++) {
                Point location = bendpoints.get(i).getLocation();
                sb.append("     " + i + ":");
                sb.append(location);
                sb.append("\n");
            }
        }

        sb.append("  . sourceAnchor: ");
        if (connFigure.getSourceAnchor() instanceof SlidableAnchor) {
            sb.append(((SlidableAnchor) connFigure.getSourceAnchor()).getTerminal() + " ==> ");
        }
        sb.append(connFigure.getSourceAnchor().getReferencePoint()).append("\n");
        sb.append("  . targetAnchor: ");
        if (connFigure.getTargetAnchor() instanceof SlidableAnchor) {
            sb.append(((SlidableAnchor) connFigure.getTargetAnchor()).getTerminal() + " ==> ");
        }
        sb.append(connFigure.getTargetAnchor().getReferencePoint()).append("\n");

        sb.append("  . points:\n");
        for (int i = 0; i < connFigure.getPoints().size(); i++) {
            sb.append("     " + i + ": " + connFigure.getPoints().getPoint(i).toString()).append("\n");
        }

        return sb.toString();
    }

    private void appendSequenceEventInfo(IDiagramElementEditPart part, StringBuilder sb) {
        if (part instanceof ISequenceEventEditPart) {
            ISequenceEvent iSequenceEvent = ((ISequenceEventEditPart) part).getISequenceEvent();
            Range range = iSequenceEvent.getVerticalRange();
            sb.append("Range: " + range.toString() + " (height = " + range.width() + ")\n");

            Rectangle properLogicalBounds = iSequenceEvent.getProperLogicalBounds();
            sb.append("Absolute Bounds: " + properLogicalBounds + ")\n");

            DDiagramElement dde = (DDiagramElement) iSequenceEvent.getNotationView().getElement();

            if (!Lifeline.viewpointElementPredicate().apply(dde) && !Iterables.isEmpty(Iterables.filter(dde.getGraphicalFilters(), AbsoluteBoundsFilter.class))) {
                AbsoluteBoundsFilter flag = Iterables.getOnlyElement(Iterables.filter(dde.getGraphicalFilters(), AbsoluteBoundsFilter.class));
                sb.append("Bounds flag: (" + flag.getX() + ", " + flag.getY() + ", " + flag.getWidth() + ", " + flag.getHeight() + ")\n\n");
            }
        }
    }

    private void appendRelativeBenpointsDetails(Edge edge, ConnectionEditPart conn, StringBuilder sb) {
        sb.append("  . bendpoints =");
        StringBuilder fromSourceSB = new StringBuilder();
        List<Point> pointsFromSource = GMFHelper.getPointsFromSource(conn);
        for (Point point : pointsFromSource) {
            fromSourceSB.append(point);
            fromSourceSB.append(" ");
        }
        StringBuilder fromTargetSB = new StringBuilder();
        List<Point> pointsFromTarget = GMFHelper.getPointsFromTarget(conn);
        for (Point point : pointsFromTarget) {
            fromTargetSB.append(point);
            fromTargetSB.append(" ");
        }
        if (fromSourceSB.toString().equals(fromTargetSB.toString())) {
            sb.append("\n     . Computed points: ");
            sb.append(fromSourceSB);
        } else {
            sb.append(" **** WARNING: points computed from source != points computed from target ****");
            sb.append("\n     . Points computed from source: ");
            sb.append(fromSourceSB);
            sb.append("\n     . Points computed from target: ");
            sb.append(fromTargetSB);
        }
        sb.append("\n     . Source vectors: ");
        RelativeBendpoints bp = (RelativeBendpoints) edge.getBendpoints();
        for (int i = 0; i < bp.getPoints().size(); i++) {
            RelativeBendpoint rbp = (RelativeBendpoint) bp.getPoints().get(i);
            sb.append("[" + rbp.getSourceX() + ", " + rbp.getSourceY() + "] ");
        }
        sb.append("\n     . Target vectors: ");
        for (int i = 0; i < bp.getPoints().size(); i++) {
            RelativeBendpoint rbp = (RelativeBendpoint) bp.getPoints().get(i);
            sb.append("[" + rbp.getTargetX() + ", " + rbp.getTargetY() + "] ");
        }
        sb.append("\n");
    }

    private Point appendAnchorDetails(ConnectionEditPart origin, IdentityAnchor anchor, ConnectionAnchor connectionAnchor, String name, StringBuilder sb) {
        sb.append(name).append(" = ");
        if (anchor != null) {
            sb.append(anchor.getId());
        } else {
            sb.append("null     ");
        }
        Point location = connectionAnchor.getReferencePoint();
        origin.getFigure().translateToRelative(location);
        sb.append(" => ").append(location).append("\n");
        return location;
    }

    /**
     * Creates the actions available to users.
     */
    @Override
    protected void createActionButtons() {
        // addFoldingToggleAction();
        // addShowOrderingsAction();
        // addStorePositionsAction();
        // addShowPositionChangesAction();
        // addShowFiguresHierarchyAction();
        // addRefreshBenpointsAction();
        // addResetBendpointsAction();
        // addExpandAction();
        // addRefreshCoverageAction();
        // addRefreshDiagramAction();
        // addStartMovidaRegistryAction();
        // addShowMovidaRegistryStatusAction();
        // addSelectReusedSiriussAction();
        // addShowCommandStackAction();
        // addSiriusSelectionAction();
        // addExtractExpressionsAction();
        // addLoadResourceWithProgressAction();
        addShowPayloadAccessLogAction();
        addClearPayloadAccessLogAction();
    }
    
    private void addShowPayloadAccessLogAction() {
        addAction("Show Payload Access Log", new Runnable() {
            public void run() {
                int max = 50;
                List<FeatureAccess> log = PayloadMarkerAdapter.INSTANCE.getAccessLog();
                int totalSize = log.size();
                int shown = Math.min(totalSize, max);
                TabularReport tr = new TabularReport(/*"Timestamp", */"EObject", "Feature");
                for (int i = log.size() > max ? log.size() - max : 0; i < log.size(); i++) {
                    FeatureAccess featureAccess = log.get(i);
                    tr.addLine(Arrays.asList(/*String.format("%tT", featureAccess.timestamp), */((Component) featureAccess.setting.getEObject()).getName(), featureAccess.setting.getEStructuralFeature().getName()));
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Showing " + shown + " of " + totalSize + " accesses.\n");
                Multiset<String> contexts = PayloadMarkerAdapter.INSTANCE.getUniqueContexts();
                sb.append("Unique contexts: " + contexts.elementSet().size()).append("\n\n");
                
                int i = 0;
                for (String stack : contexts.elementSet()) {
                    int count = contexts.count(stack);
                    sb.append("Context #" + i++ + " (" + count + " occurrences)").append("\n");
                    sb.append(stack).append("\n");
                }

                sb.append("\n").append(tr.print()).append("\n");
                setText(sb.toString());
            }
        });
    }
    
    private void addClearPayloadAccessLogAction() {
        addAction("Clear Payload Access Log", new Runnable() {
            public void run() {
                PayloadMarkerAdapter.INSTANCE.clearAccessLog();
            }
        });
    }

    private void addLoadResourceWithProgressAction() {
        addAction("Load with progress", new Runnable() {
            public void run() {
                FileDialog dia = new FileDialog(getSite().getShell(), SWT.OPEN);
                dia.setFilterExtensions(new String[] { "*.ecore" });
                final String path = dia.open();
                final Resource[] result = new Resource[1];
                if (path != null) {
                    try {
                        IRunnableWithProgress op = new IRunnableWithProgress() {
                            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                                ResourceSet rs = new ResourceSetImpl();
                                final SubMonitor loadingMonitor = SubMonitor.convert(monitor, (int) new File(path).length());
                                List<URIHandler> handlers = Lists.newArrayList(URIHandler.DEFAULT_HANDLERS);
                                handlers.set(1, new FileURIHandlerImpl() {
                                    @Override
                                    public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
                                        String filePath = uri.toFileString();
                                        File file = new File(filePath);
                                        InputStream inputStream = new ProgressMonitorInputStream(new FileInputStream(file), file.length(), 1, loadingMonitor) {
                                            private long previousRead = 0;

                                            @Override
                                            protected void updateMonitor(long bytesRead, long size, IProgressMonitor monitor) {
                                                long progress = bytesRead - previousRead;
                                                while (progress > Integer.MAX_VALUE) {
                                                    loadingMonitor.worked(Integer.MAX_VALUE);
                                                    progress -= Integer.MAX_VALUE;
                                                }
                                                loadingMonitor.worked((int) progress);
                                                previousRead = bytesRead;
                                            }
                                        };
                                        Map<Object, Object> response = getResponse(options);
                                        if (response != null) {
                                            response.put(URIConverter.RESPONSE_TIME_STAMP_PROPERTY, file.lastModified());
                                        }
                                        return inputStream;
                                    }

                                });
                                rs.setURIConverter(new ExtensibleURIConverterImpl(handlers, ContentHandler.Registry.INSTANCE.contentHandlers()));
                                result[0] = rs.getResource(URI.createFileURI(path), true);
                            }
                        };
                        new ProgressMonitorDialog(getSite().getShell()).run(true, false, op);
                    } catch (InvocationTargetException e) {
                        // handle exception
                    } catch (InterruptedException e) {
                        // handle cancelation
                    }
                    if (result[0] != null) {
                        setText("Loaded resource " + result[0] + " with " + Iterables.size(AllContents.of(result[0].getContents().get(0))) + " elements.");
                    } else {
                        setText("Failed loading resource at " + path);
                    }
                }
            }
        });
    }

    private void addExtractExpressionsAction() {
        addAction("Extract Expressions", new Runnable() {
            public void run() {
                FileDialog dia = new FileDialog(getSite().getShell(), SWT.OPEN | SWT.MULTI);
                dia.setFilterExtensions(new String[] { "*.odesign" });
                String path = dia.open();
                List<String[]> lines = Lists.newArrayList();
                if (path != null) {
                    for (String file : dia.getFileNames()) {
                        ResourceSet rs = new ResourceSetImpl();
                        Resource vsm = rs.getResource(URI.createFileURI(dia.getFilterPath() + "/" + file), true);
                        for (EObject obj : AllContents.of(vsm.getContents().get(0))) {
                            EClass klass = obj.eClass();
                            for (EAttribute attr : klass.getEAllAttributes()) {
                                if (attr.getEType() == DescriptionPackage.Literals.INTERPRETED_EXPRESSION) {
                                    String value = Objects.firstNonNull((String) obj.eGet(attr), "").trim();
                                    if (value.length() > 100) {
                                        value = value.substring(0, 97) + "...";
                                    }
                                    lines.add(new String[] { value, attr.getEContainingClass().getName() + "." + attr.getName() });
                                }
                            }
                        }
                    }
                }
                StringBuilder result = new StringBuilder();
                for (String[] line : lines) {
                    result.append(line[0] + "\n");
                }
                result.append("Total: " + lines.size() + " expressions");
                setText(result.toString());
                // Collections.sort(lines, new Comparator<String[]>() {
                // public int compare(String[] o1, String[] o2) {
                // return o1[0].compareTo(o2[0]);
                // }
                // });
                // TabularReport report = new TabularReport("Expression",
                // "Feature");
                // for (String[] line : lines) {
                // report.addLine(line[0], line[1]);
                // }
                // setText(report.print());
            }
        });
    }

    private ViewpointSelection currentSelection = null;

    private void addSiriusSelectionAction() {
        addAction("Sirius Selection", new Runnable() {
            public void run() {
                ViewpointRegistry reg = (ViewpointRegistry) org.eclipse.sirius.business.api.componentization.ViewpointRegistry.getInstance();
                if (currentSelection == null) {
                    currentSelection = new ViewpointSelection(reg);
                }
                ViewpointSelectionDialog dlg = new ViewpointSelectionDialog(getSite().getShell(), reg, currentSelection, null);
                if (dlg.open() == Window.OK) {
                    // Do nothing here.
                }
            }
        });
    }

    private void addShowCommandStackAction() {
        addAction("Show CommandStack", new Runnable() {
            public void run() {
                IUndoContext undoContext = getUndoContext(selection);
                if (undoContext != null) {
                    TabularReport report = new TabularReport("IUndoableOperation's name");
                    IUndoableOperation[] redoableOperations = OperationHistoryFactory.getOperationHistory().getRedoHistory(undoContext);
                    if (redoableOperations != null) {
                        for (int i = redoableOperations.length - 1; i >= 0; i--) {
                            IUndoableOperation redoableOperation = redoableOperations[i];
                            report.addLine(redoableOperation.getLabel());
                        }
                    }
                    report.addLine("---------");
                    IUndoableOperation[] undoableOperations = OperationHistoryFactory.getOperationHistory().getUndoHistory(undoContext);
                    if (undoableOperations != null) {
                        for (int i = undoableOperations.length - 1; i >= 0; i--) {
                            IUndoableOperation undoableOperation = undoableOperations[i];
                            report.addLine(undoableOperation.getLabel());
                        }
                    }
                    setText(report.print());
                }
            }

            private IUndoContext getUndoContext(Object selection) {
                IUndoContext undoContext = IOperationHistory.GLOBAL_UNDO_CONTEXT;
                EObject eObject = null;
                if (selection instanceof EObject) {
                    eObject = (EObject) selection;
                } else if (selection instanceof IAdaptable) {
                    IAdaptable adaptable = (IAdaptable) selection;
                    eObject = (EObject) adaptable.getAdapter(EObject.class);
                }
                if (eObject != null) {
                    TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(eObject);
                    if (domain == null && eObject instanceof Session) {
                        Session session = (Session) eObject;
                        domain = session.getTransactionalEditingDomain();
                    }
                    if (domain != null && domain.getCommandStack() instanceof IWorkspaceCommandStack) {
                        IWorkspaceCommandStack workspaceCommandStack = (IWorkspaceCommandStack) domain.getCommandStack();
                        undoContext = workspaceCommandStack.getDefaultUndoContext();
                    }
                }
                return undoContext;
            }
        });
    }

    private void addSelectReusedSiriussAction() {
        addAction("Select reused", new Runnable() {
            public void run() {
                if (selection instanceof Viewpoint) {
                    final Option<Set<URI>> sel = new ViewpoitnDependenciesSelectionDialog((Viewpoint) selection).selectReusedViewpoints(getSite().getShell());
                    if (sel.some()) {
                        ((Viewpoint) selection).getReuses().clear();
                        Iterables.addAll(((Viewpoint) selection).getReuses(), sel.get());
                    }
                }
            }
        });
    }

    private void addShowMovidaRegistryStatusAction() {
        addAction("Show Registry Status", new Runnable() {
            public void run() {
                if (registry != null) {
                    StringBuilder status = new StringBuilder();
                    registry.dumpStatus(status);
                    setText(status.toString());
                }
            }
        });
    }

    private void addStartMovidaRegistryAction() {
        addAction("Start Registry", new Runnable() {
            public void run() {
                if (registry != null) {
                    registry.stop();
                }
                registry = new ViewpointRegistry();
                registry.addListener(new ViewpointRegistryListener() {
                    public void registryChanged(ViewpointRegistry registry, Set<URI> removed, Set<URI> added, Set<URI> changed) {
                        System.out.println();
                        System.out.println("Added:");
                        for (URI a : added) {
                            System.out.println(" - " + a);
                        }
                        System.out.println("Removed:");
                        for (URI r : removed) {
                            System.out.println(" - " + r);
                        }
                        System.out.println("Changed:");
                        for (URI c : changed) {
                            System.out.println(" - " + c);
                        }
                    }
                });
                registry.start();
            }
        });
    }

    private void addRefreshDiagramAction() {
        addAction("Refresh diagram", new Runnable() {
            public void run() {
                if (selection instanceof DiagramEditPart) {
                    final DiagramEditPart diag = (DiagramEditPart) selection;
                    diag.refresh();
                    for (IGraphicalEditPart child : Iterables.filter(diag.getChildren(), IGraphicalEditPart.class)) {
                        child.refresh();
                    }
                }
            }
        });
    }

    private void addRefreshCoverageAction() {
        addAction("Refresh coverage", new Runnable() {
            public void run() {
                if (selection instanceof SequenceDiagramEditPart) {
                    final SequenceDiagramEditPart sdep = (SequenceDiagramEditPart) selection;
                    TransactionalEditingDomain ted = sdep.getEditingDomain();
                    RefreshGraphicalCoverage refreshGraphicalCoverage = new RefreshGraphicalCoverage(ted, sdep);
                    sdep.getEditingDomain().getCommandStack().execute(refreshGraphicalCoverage);
                }
            }
        });
    }

    private void addExpandAction() {
        addAction("Expand", new Runnable() {
            public void run() {
                if (selection instanceof SequenceDiagramEditPart) {
                    final int start = Integer.parseInt(askStringFromUser("Expansion", "Start y", "0"));
                    final int size = Integer.parseInt(askStringFromUser("Expansion", "Size", "0"));
                    final SequenceDiagramEditPart sdep = (SequenceDiagramEditPart) selection;
                    TransactionalEditingDomain ted = sdep.getEditingDomain();
                    RecordingCommand verticalSpaceExpansion = CommandFactory.createRecordingCommand(ted, new VerticalSpaceExpansion(sdep.getSequenceDiagram(), new Range(start, start + size), 0,
                            Collections.<ISequenceEvent> emptyList()));
                    sdep.getEditingDomain().getCommandStack().execute(verticalSpaceExpansion);
                }
            }
        });
    }

    private void addRefreshBenpointsAction() {
        addAction("Refresh bendpoints", new Runnable() {
            public void run() {
                if (selection instanceof SequenceMessageEditPart) {
                    final SequenceMessageEditPart smep = (SequenceMessageEditPart) selection;
                    TransactionalEditingDomain ted = smep.getEditingDomain();
                    RefreshBendpoints refreshBendpoints = new RefreshBendpoints(ted, smep);
                    smep.getEditingDomain().getCommandStack().execute(refreshBendpoints);
                } else if (selection instanceof SequenceDiagramEditPart) {
                    for (final SequenceMessageEditPart smep : Iterables.filter(((SequenceDiagramEditPart) selection).getChildren(), SequenceMessageEditPart.class)) {
                        TransactionalEditingDomain ted = smep.getEditingDomain();
                        RefreshBendpoints refreshBendpoints = new RefreshBendpoints(ted, smep);
                        smep.getEditingDomain().getCommandStack().execute(refreshBendpoints);
                    }
                }
            }
        });

    }

    private void addResetBendpointsAction() {
        addAction("Reset bendpoints", new Runnable() {
            public void run() {
                if (selection instanceof SequenceDiagramEditPart) {
                    final SequenceDiagramEditPart sdep = (SequenceDiagramEditPart) selection;
                    TransactionalEditingDomain ted = sdep.getEditingDomain();
                    RecordingCommand verticalSpaceExpansion = CommandFactory.createRecordingCommand(ted,
                            new VerticalSpaceExpansion(sdep.getSequenceDiagram(), new Range(0, 0), 0, Lists.<ISequenceEvent> newArrayList()));
                    sdep.getEditingDomain().getCommandStack().execute(verticalSpaceExpansion);
                }
            }
        });
    }

    /**
     * Reads and remembers the vertical position of each element in a sequence
     * diagram. Use in conjunction with "Show Position Changes".
     */
    private void addStorePositionsAction() {
        addAction("Store positions", new Runnable() {
            public void run() {
                if (selection instanceof SequenceDiagramEditPart) {
                    SequenceDiagramEditPart sdep = (SequenceDiagramEditPart) selection;
                    storedPositions = readVerticalPositions(sdep);
                }
            }
        });
    }

    /**
     * Prints a report of which elements changed position in a sequence diagram
     * since the last call to "Store positions". Only the elements whose
     * position is different are shown (i.e. an empty report means nothing
     * changed).
     */
    private void addShowPositionChangesAction() {
        addAction("Show Position Changes", new Runnable() {
            public void run() {
                if (selection instanceof SequenceDiagramEditPart) {
                    SequenceDiagramEditPart sdep = (SequenceDiagramEditPart) selection;
                    if (storedPositions != null) {
                        Map<EObject, Integer> newPositions = readVerticalPositions(sdep);
                        List<EObject> elements = Lists.newArrayList(newPositions.keySet());
                        Collections.sort(elements, Ordering.natural().onResultOf(Functions.forMap(newPositions)));
                        TabularReport report = new TabularReport("Semantic element", "Old Position", "New Position", "deltaY");
                        AdapterFactoryLabelProvider lp = new AdapterFactoryLabelProvider(getAdapterFactory());
                        for (EObject element : elements) {
                            Integer oldY = storedPositions.get(element);
                            Integer newY = newPositions.get(element);
                            if (oldY != null && !oldY.equals(newY)) {
                                report.addLine(lp.getText(element), String.valueOf(oldY), String.valueOf(newY), String.valueOf(newY - oldY));
                            } else if (oldY == null) {
                                report.addLine(lp.getText(element), "-", String.valueOf(newY), "n/a");
                            } else if (newY == VerticalPositionFunction.INVALID_POSITION) {
                                report.addLine(lp.getText(element), String.valueOf(oldY), "-", "n/a");
                            }
                        }
                        setText(report.print());
                    }
                }
            }
        });
    }

    private Map<EObject, Integer> readVerticalPositions(SequenceDiagramEditPart sdep) {
        SequenceDDiagram diag = (SequenceDDiagram) sdep.resolveSemanticElement();
        VerticalPositionFunction vpf = new VerticalPositionFunction(diag);
        Map<EObject, Integer> positions = Maps.newHashMap();
        for (EventEnd end : diag.getGraphicalOrdering().getEventEnds()) {
            positions.put(end.getSemanticEnd(), (int) vpf.apply(end));
        }
        return positions;
    }

    /**
     * Prints a report of the graphical and semantic orderings on a sequence
     * diagram. For elements which have a valid graphical position, it is also
     * reported.
     */
    private void addShowOrderingsAction() {
        addAction("Orderings", new Runnable() {
            public void run() {
                if (selection instanceof SequenceDiagramEditPart) {
                    AdapterFactoryLabelProvider lp = new AdapterFactoryLabelProvider(getAdapterFactory());
                    SequenceDiagramEditPart sdep = (SequenceDiagramEditPart) selection;
                    SequenceDDiagram diag = (SequenceDDiagram) sdep.resolveSemanticElement();
                    VerticalPositionFunction vpf = new VerticalPositionFunction(diag);
                    Iterator<EventEnd> go = diag.getGraphicalOrdering().getEventEnds().iterator();
                    Iterator<EventEnd> so = diag.getSemanticOrdering().getEventEnds().iterator();
                    TabularReport report = new TabularReport("Semantic", "Graphical", "Sync?", "Y");
                    while (so.hasNext() || go.hasNext()) {
                        List<String> line = Lists.newArrayList();

                        final EObject sosEnd;
                        if (so.hasNext()) {
                            EventEnd soEnd = so.next();
                            sosEnd = soEnd.getSemanticEnd();
                            line.add(lp.getText(sosEnd));
                        } else {
                            sosEnd = null;
                            line.add("<none>");
                        }

                        final EObject gosEnd;
                        final int y;
                        if (go.hasNext()) {
                            EventEnd goEnd = go.next();
                            gosEnd = goEnd.getSemanticEnd();
                            line.add(lp.getText(gosEnd));
                            y = vpf.apply(goEnd);
                        } else {
                            gosEnd = null;
                            line.add("<none>");
                            y = -1;
                        }

                        if (sosEnd == gosEnd) {
                            line.add("yes");
                        } else {
                            line.add("NO");
                        }
                        line.add(y != -1 ? String.valueOf(y) : "n/a");
                        report.addLine(line);
                    }
                    setText(report.print());
                }
            }
        });
    }

    private void addFoldingToggleAction() {
        addAction("Toggle folding", new Runnable() {
            public void run() {
                if (selection instanceof IAbstractDiagramNodeEditPart) {
                    IAbstractDiagramNodeEditPart part = (IAbstractDiagramNodeEditPart) selection;
                    EdgeTargetQuery query = new EdgeTargetQuery((EdgeTarget) part.resolveDiagramElement());
                    boolean isFoldingPoint = query.isFoldingPoint();
                    if (isFoldingPoint) {
                        part.getEditingDomain().getCommandStack().execute(new ToggleFoldingStateCommand(part.getEditingDomain(), part));
                    }
                }
            }
        });
    }

    /**
     * Show details about each figure in the Draw2D hierarchy starting from the
     * current selection's figure. See {@link #figureDetails(IFigure)} for what
     * information is actually shown for each figure.
     */
    private void addShowFiguresHierarchyAction() {
        addAction("Show figures", new Runnable() {
            public void run() {
                if (selection instanceof IAbstractDiagramNodeEditPart) {
                    IAbstractDiagramNodeEditPart part = (IAbstractDiagramNodeEditPart) selection;
                    StringBuilder sb = new StringBuilder();
                    showFigures(part.getFigure(), 0, sb);
                    setText(sb.toString());
                }
            }
        });
    }

    private void showFigures(IFigure figure, int level, StringBuilder out) {
        for (int i = 0; i < level; i++) {
            out.append("  ");
        }
        out.append(figureDetails(figure)).append("\n");
        for (IFigure child : Iterables.filter(figure.getChildren(), IFigure.class)) {
            showFigures(child, level + 1, out);
        }
    }

    private String figureDetails(IFigure figure) {
        return "Opaque: " + figure.isOpaque() + " Color: " + figure.getBackgroundColor() + " Class: " + figure.getClass().getSimpleName() + " Size: " + figure.getBounds().getSize();
    }

    private static class RefreshGraphicalCoverage extends RecordingCommand {

        private SequenceDiagramEditPart sdep;

        public RefreshGraphicalCoverage(TransactionalEditingDomain domain, SequenceDiagramEditPart sdep) {
            super(domain);
            this.sdep = sdep;
        }

        @Override
        protected void doExecute() {
            new SequenceDiagramLayout(sdep).refreshGraphicalCoverage();
        }

    }

    private static class RefreshBendpoints extends RecordingCommand {

        private SequenceMessageEditPart smep;

        public RefreshBendpoints(TransactionalEditingDomain domain, SequenceMessageEditPart smep) {
            super(domain);
            this.smep = smep;
        }

        @Override
        protected void doExecute() {
            smep.refreshBendpoints();
        }

    }
}
