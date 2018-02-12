package com.vp.plugin.sample.activitydiagramswimlane.actions;

import java.awt.Point;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.diagram.IActivityDiagramUIModel;
import com.vp.plugin.diagram.IDiagramTypeConstants;
import com.vp.plugin.diagram.IShapeTypeConstants;
import com.vp.plugin.diagram.shape.IActivityActionUIModel;
import com.vp.plugin.diagram.shape.IActivityFinalUIModel;
import com.vp.plugin.diagram.shape.IActivityPartitionHeaderUIModel;
import com.vp.plugin.diagram.shape.IActivitySwimlane2CompartmentUIModel;
import com.vp.plugin.diagram.shape.IActivitySwimlane2NewUIModel;
import com.vp.plugin.diagram.shape.IDecisionNodeUIModel;
import com.vp.plugin.diagram.shape.IForkNodeUIModel;
import com.vp.plugin.diagram.shape.IInitialNodeUIModel;
import com.vp.plugin.diagram.shape.IJoinNodeUIModel;
import com.vp.plugin.diagram.shape.IMergeNodeUIModel;
import com.vp.plugin.diagram.shape.IObjectNodeUIModel;
import com.vp.plugin.model.IActivityAction;
import com.vp.plugin.model.IActivityFinalNode;
import com.vp.plugin.model.IActivityObjectFlow;
import com.vp.plugin.model.IActivityPartition;
import com.vp.plugin.model.IActivitySwimlane2;
import com.vp.plugin.model.IControlFlow;
import com.vp.plugin.model.IDecisionNode;
import com.vp.plugin.model.IForkNode;
import com.vp.plugin.model.IInitialNode;
import com.vp.plugin.model.IJoinNode;
import com.vp.plugin.model.IMergeNode;
import com.vp.plugin.model.IObjectNode;
import com.vp.plugin.model.factory.IModelElementFactory;

public class ActivityDiagramSwimlaneActionControl implements VPActionController {
	
	private static int PARTITION_HEADER_HEIGHT = 15;
	
	@Override
	public void performAction(VPAction arg0) {
		// Create activity diagram
		DiagramManager diagramManager = ApplicationManager.instance().getDiagramManager();
		IActivityDiagramUIModel diagram = (IActivityDiagramUIModel) diagramManager.createDiagram(IDiagramTypeConstants.DIAGRAM_TYPE_ACTIVITY_DIAGRAM);
		
		// Create swimlane model
		IActivitySwimlane2 swimlane = IModelElementFactory.instance().createActivitySwimlane2();
		// Create the view of swimlane
		IActivitySwimlane2NewUIModel swimlaneShape = (IActivitySwimlane2NewUIModel) diagramManager.createDiagramElement(diagram, swimlane);
		// Specify the size and location of the swimlane
		swimlaneShape.setBounds(50, 80, 850, 400);
		// Trigger the automatic calculate caption position
		swimlaneShape.setRequestResetCaption(true);		
		
		// Create partitions for swimlane
		IActivityPartition partitionSeattle = IModelElementFactory.instance().createActivityPartition();
		partitionSeattle.setName("Seattle");
		// Specify it is a vertical partition
		swimlane.addVerticalPartition(partitionSeattle);
		// Add the partition model as child model of swimlane
		swimlane.addChild(partitionSeattle);
		// Create vertical partition header shape
		IActivityPartitionHeaderUIModel partitionSeattleHeader = (IActivityPartitionHeaderUIModel) diagramManager.createDiagramElement(diagram, partitionSeattle);
		// Specify the same as a vertical partition
		partitionSeattleHeader.setHorizontal(false);
		// Making the swimlane shape and partition header shape reference to each other 
		partitionSeattleHeader.setSwimlane(swimlaneShape);
		swimlaneShape.addChild(partitionSeattleHeader);
		// Specify the location of the partition header. Make sure it is within the bounds of the swimlane
		partitionSeattleHeader.setBounds(50+PARTITION_HEADER_HEIGHT, 80, 450-PARTITION_HEADER_HEIGHT, PARTITION_HEADER_HEIGHT);
		// Trigger the automatic calculate caption position
		partitionSeattleHeader.setRequestResetCaption(true);
		
		
		IActivityPartition partitionReno = IModelElementFactory.instance().createActivityPartition();
		partitionReno.setName("Reno");
		swimlane.addVerticalPartition(partitionReno);
		swimlane.addChild(partitionReno);
		IActivityPartitionHeaderUIModel partitionRenoHeader = (IActivityPartitionHeaderUIModel) diagramManager.createDiagramElement(diagram, partitionReno);
		partitionRenoHeader.setHorizontal(false);
		partitionRenoHeader.setSwimlane(swimlaneShape);
		swimlaneShape.addChild(partitionRenoHeader);
		partitionRenoHeader.setBounds(500, 80, 400, PARTITION_HEADER_HEIGHT);
		partitionRenoHeader.setRequestResetCaption(true);
				
		IActivityPartition partitionOrderProcessor = IModelElementFactory.instance().createActivityPartition();
		partitionOrderProcessor.setName("Order Processor");
		swimlane.addHorizontalPartition(partitionOrderProcessor);
		swimlane.addChild(partitionOrderProcessor);
		IActivityPartitionHeaderUIModel partitionOrderProcessorHeader = (IActivityPartitionHeaderUIModel) diagramManager.createDiagramElement(diagram, partitionOrderProcessor);
		partitionOrderProcessorHeader.setHorizontal(true);
		partitionOrderProcessorHeader.setSwimlane(swimlaneShape);
		swimlaneShape.addChild(partitionOrderProcessorHeader);
		partitionOrderProcessorHeader.setBounds(50, 80+PARTITION_HEADER_HEIGHT, 15, 200-PARTITION_HEADER_HEIGHT);
		partitionOrderProcessorHeader.setRequestResetCaption(true);
		

		IActivityPartition partitionAccountingCleark = IModelElementFactory.instance().createActivityPartition();
		partitionAccountingCleark.setName("Accounting Clerk");
		swimlane.addHorizontalPartition(partitionAccountingCleark);
		swimlane.addChild(partitionAccountingCleark);
		IActivityPartitionHeaderUIModel partitionAccountingCleakHeader = (IActivityPartitionHeaderUIModel) diagramManager.createDiagramElement(diagram, partitionAccountingCleark);
		partitionAccountingCleakHeader.setHorizontal(true);		
		partitionAccountingCleakHeader.setSwimlane(swimlaneShape);
		swimlaneShape.addChild(partitionAccountingCleakHeader);
		partitionAccountingCleakHeader.setBounds(50, 280, PARTITION_HEADER_HEIGHT, 200);
		partitionAccountingCleakHeader.setRequestResetCaption(true);
		
		// Associate the ID of partition header to the vertical and horizontal partition
		swimlaneShape.setVerticalPartitionIds(new String[] {partitionSeattleHeader.getId(), partitionRenoHeader.getId()});
		swimlaneShape.setHorizontalPartitionIds(new String[] {partitionOrderProcessorHeader.getId(), partitionAccountingCleakHeader.getId()});		
		
		// Create compartment shape between Seattle partition and Order Processor partition
		IActivitySwimlane2CompartmentUIModel cellSeattleOrderProcessor = (IActivitySwimlane2CompartmentUIModel) diagramManager.createDiagramElement(diagram, IShapeTypeConstants.SHAPE_TYPE_ACTIVITY_SWIMLANE2_COMPARTMENT);
		// Specify the ID of the vertical and horizontal header
		cellSeattleOrderProcessor.setVerticalPartitionId(partitionSeattleHeader.getId());
		cellSeattleOrderProcessor.setHorizontalPartitionId(partitionOrderProcessorHeader.getId());
		// Specify the bounds of the compartment
		cellSeattleOrderProcessor.setBounds(50+PARTITION_HEADER_HEIGHT, 80, 450-PARTITION_HEADER_HEIGHT, 200);
		// Add the compartment shape as child of swimlane shape
		swimlaneShape.addChild(cellSeattleOrderProcessor);
		
		// Create Initial Node Model
		IInitialNode initialNode = IModelElementFactory.instance().createInitialNode();
		// Add the initial node model as child of the swimlane
		swimlane.addChild(initialNode);
		// Specify the partition(s) which containing the initial node
		partitionOrderProcessor.addContainedElement(initialNode);
		partitionSeattle.addContainedElement(initialNode);
		// Create Initial Node Shape
		IInitialNodeUIModel initialNodeShape = (IInitialNodeUIModel) diagramManager.createDiagramElement(diagram, initialNode);
		// Specify the bounds of the initial node on diagram
		initialNodeShape.setBounds(80, 160, 20, 20);
		// Add the initial node shape to the child of the compartment
		cellSeattleOrderProcessor.addChild(initialNodeShape);		
		
		// Create Receive Order action model
		IActivityAction actionReceiveOrder = IModelElementFactory.instance().createActivityAction();
		swimlane.addChild(actionReceiveOrder);
		actionReceiveOrder.setName("Receive Order");
		partitionOrderProcessor.addContainedElement(actionReceiveOrder);
		partitionSeattle.addContainedElement(actionReceiveOrder);
		IActivityActionUIModel actionShapeReceiveOrder = (IActivityActionUIModel) diagramManager.createDiagramElement(diagram, actionReceiveOrder);
		actionShapeReceiveOrder.setBounds(140, 150, 100, 40);
		actionShapeReceiveOrder.setRequestResetCaption(true);
		cellSeattleOrderProcessor.addChild(actionShapeReceiveOrder);
		
		// Create control flow model between initial node and Receive Order action
		IControlFlow flowInitialReceiveOrder = IModelElementFactory.instance().createControlFlow();
		// Add the control flow model as child to swimlane
		swimlane.addChild(flowInitialReceiveOrder);
		// Specify the from/to element
		flowInitialReceiveOrder.setFrom(initialNode);
		flowInitialReceiveOrder.setTo(actionReceiveOrder);
		// CReate control flow connector on diagram.
		diagramManager.createConnector(diagram, flowInitialReceiveOrder, initialNodeShape, actionShapeReceiveOrder, null);
		
		// Create Decision node
		IDecisionNode decisionOrderAccepted = IModelElementFactory.instance().createDecisionNode();
		swimlane.addChild(decisionOrderAccepted);
		decisionOrderAccepted.setName("[order accepted]");
		partitionOrderProcessor.addContainedElement(decisionOrderAccepted);
		partitionSeattle.addContainedElement(decisionOrderAccepted);
		IDecisionNodeUIModel decisionShapeOrderAccepted = (IDecisionNodeUIModel) diagramManager.createDiagramElement(diagram, decisionOrderAccepted);
		decisionShapeOrderAccepted.setBounds(280, 150, 20, 40);
		decisionShapeOrderAccepted.setRequestResetCaption(true);
		cellSeattleOrderProcessor.addChild(decisionShapeOrderAccepted);
		
		// Create control flow model between Receive Order action and Decision node
		IControlFlow flowReceiveOrderDecision = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowReceiveOrderDecision);
		flowReceiveOrderDecision.setFrom(actionReceiveOrder);
		flowReceiveOrderDecision.setTo(decisionOrderAccepted);
		diagramManager.createConnector(diagram, flowReceiveOrderDecision, actionShapeReceiveOrder, decisionShapeOrderAccepted, 
										new Point[]{ new Point(240,170), new Point(282,170)});		
		
		// Create Fill Order action model
		IActivityAction actionFillOrder = IModelElementFactory.instance().createActivityAction();
		swimlane.addChild(actionFillOrder);
		actionFillOrder.setName("Fill Order");
		partitionOrderProcessor.addContainedElement(actionFillOrder);
		partitionSeattle.addContainedElement(actionFillOrder);
		IActivityActionUIModel actionShapeFillOrder = (IActivityActionUIModel) diagramManager.createDiagramElement(diagram, actionFillOrder);
		actionShapeFillOrder.setBounds(340, 150, 100, 40);
		actionShapeFillOrder.setRequestResetCaption(true);
		cellSeattleOrderProcessor.addChild(actionShapeFillOrder);
		
		// Create control flow model between Decision node and Fill Order action
		IControlFlow flowDecisionFillOrder = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowDecisionFillOrder);
		flowDecisionFillOrder.setFrom(decisionOrderAccepted);
		flowDecisionFillOrder.setTo(actionFillOrder);
		diagramManager.createConnector(diagram, flowDecisionFillOrder, decisionShapeOrderAccepted, actionShapeFillOrder, null);		
		
		// Create Fork node
		IForkNode fork = IModelElementFactory.instance().createForkNode();
		swimlane.addChild(fork);
		partitionOrderProcessor.addContainedElement(fork);
		partitionSeattle.addContainedElement(fork);
		IForkNodeUIModel forkShape = (IForkNodeUIModel) diagramManager.createDiagramElement(diagram, fork);
		forkShape.setBounds(470, 140, 10, 60);
		cellSeattleOrderProcessor.addChild(forkShape);
		
		// Create control flow between Fill Order action and Fork node
		IControlFlow flowFillOrderFork = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowFillOrderFork);
		flowFillOrderFork.setFrom(actionFillOrder);
		flowFillOrderFork.setTo(fork);
		diagramManager.createConnector(diagram, flowFillOrderFork, actionShapeFillOrder, forkShape, new Point[] {new Point(440, 170), new Point(470,170)});
		
		// Create compartment between Reno partition and Order Processor partition
		IActivitySwimlane2CompartmentUIModel cellRenoOrderProcessor = (IActivitySwimlane2CompartmentUIModel) diagramManager.createDiagramElement(diagram, IShapeTypeConstants.SHAPE_TYPE_ACTIVITY_SWIMLANE2_COMPARTMENT);
		cellRenoOrderProcessor.setVerticalPartitionId(partitionRenoHeader.getId());
		cellRenoOrderProcessor.setHorizontalPartitionId(partitionOrderProcessorHeader.getId());
		cellRenoOrderProcessor.setBounds(500, 80, 400, 200);
		swimlaneShape.addChild(cellRenoOrderProcessor);
		
		// Create Ship Order action
		IActivityAction actionShipOrder = IModelElementFactory.instance().createActivityAction();
		swimlane.addChild(actionShipOrder);
		actionShipOrder.setName("Ship Order");
		partitionOrderProcessor.addContainedElement(actionShipOrder);
		partitionReno.addContainedElement(actionShipOrder);
		IActivityActionUIModel actionShapeShipOrder = (IActivityActionUIModel) diagramManager.createDiagramElement(diagram, actionShipOrder);
		actionShapeShipOrder.setBounds(520, 150, 100, 40);
		actionShapeShipOrder.setRequestResetCaption(true);
		cellRenoOrderProcessor.addChild(actionShapeShipOrder);
		
		// Create control flow between Fork and Ship Order
		IControlFlow flowForkShipOrder = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowForkShipOrder);
		flowForkShipOrder.setFrom(fork);
		flowForkShipOrder.setTo(actionShipOrder);
		diagramManager.createConnector(diagram, flowForkShipOrder, forkShape, actionShapeShipOrder, new Point[] {new Point(480, 160), new Point(520,160)});
		
		// Create Join node
		IJoinNode join = IModelElementFactory.instance().createJoinNode();
		swimlane.addChild(join);
		partitionOrderProcessor.addContainedElement(join);
		partitionReno.addContainedElement(join);
		IJoinNodeUIModel joinShape = (IJoinNodeUIModel) diagramManager.createDiagramElement(diagram, join);
		joinShape.setBounds(660, 140, 10, 60);
		cellRenoOrderProcessor.addChild(joinShape);
		
		// Create control flow between Ship Order and Join node
		IControlFlow flowShipOrderJoin = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowShipOrderJoin);
		flowShipOrderJoin.setFrom(actionShipOrder);
		flowShipOrderJoin.setTo(join);
		diagramManager.createConnector(diagram, flowShipOrderJoin, actionShapeShipOrder, joinShape, new Point[] {new Point(620, 160), new Point(660, 160)});
		
		// Create Merge node
		IMergeNode merge = IModelElementFactory.instance().createMergeNode();
		swimlane.addChild(merge);
		partitionOrderProcessor.addContainedElement(merge);
		partitionReno.addContainedElement(merge);
		IMergeNodeUIModel mergeShape = (IMergeNodeUIModel) diagramManager.createDiagramElement(diagram, merge);
		mergeShape.setBounds(710, 150, 20, 40);
		cellRenoOrderProcessor.addChild(mergeShape);
		
		// Create control flow between Join and Merge node
		IControlFlow flowJoinMerge = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowJoinMerge);
		flowJoinMerge.setFrom(join);
		flowJoinMerge.setTo(merge);
		diagramManager.createConnector(diagram, flowJoinMerge, joinShape, mergeShape, new Point[] {new Point(670,170), new Point(712, 170)});
		
		// Create control flow between Decision and Merge
		IControlFlow flowDecisionMerge = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowDecisionMerge);
		flowDecisionMerge.setFrom(decisionOrderAccepted);
		flowDecisionMerge.setTo(merge);
		diagramManager.createConnector(diagram, flowDecisionMerge, decisionShapeOrderAccepted, mergeShape, 
										new Point[] {new Point(290,157), new Point(290,115), new Point(720,115), new Point(720,157)});
		
		// Create Close Order action
		IActivityAction actionCloseOrder = IModelElementFactory.instance().createActivityAction();
		swimlane.addChild(actionCloseOrder);
		actionCloseOrder.setName("Close Order");
		partitionOrderProcessor.addContainedElement(actionCloseOrder);
		partitionReno.addContainedElement(actionCloseOrder);
		IActivityActionUIModel actionShapeCloseOrder = (IActivityActionUIModel) diagramManager.createDiagramElement(diagram, actionCloseOrder);
		actionShapeCloseOrder.setBounds(760, 150, 100, 40);
		actionShapeCloseOrder.setRequestResetCaption(true);
		cellRenoOrderProcessor.addChild(actionShapeCloseOrder);
		
		// Create control flow between Merge and Close Order action
		IControlFlow flowMergeCloseOrder = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowMergeCloseOrder);
		flowMergeCloseOrder.setFrom(merge);
		flowMergeCloseOrder.setTo(actionCloseOrder);
		diagramManager.createConnector(diagram, flowMergeCloseOrder, mergeShape, actionShapeCloseOrder, new Point[] {new Point(728,170), new Point(760,170)});
		
		// Create final node model
		IActivityFinalNode finalNode = IModelElementFactory.instance().createActivityFinalNode();
		swimlane.addChild(finalNode);
		partitionOrderProcessor.addContainedElement(finalNode);
		partitionReno.addContainedElement(finalNode);
		IActivityFinalUIModel shapeFinalNode = (IActivityFinalUIModel) diagramManager.createDiagramElement(diagram, finalNode);
		shapeFinalNode.setBounds(870, 230, 20, 20);
		cellRenoOrderProcessor.addChild(shapeFinalNode);
		
		// Create control flow between Close Order action and Final node
		IControlFlow flowCloseOrderFinal = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowCloseOrderFinal);
		flowCloseOrderFinal.setFrom(actionCloseOrder);
		flowCloseOrderFinal.setTo(finalNode);
		diagramManager.createConnector(diagram, flowCloseOrderFinal, actionShapeCloseOrder, shapeFinalNode, null);
		
		// Create compartment between Seattle partition and Accounting Cleark partition 
		IActivitySwimlane2CompartmentUIModel cellSeattleAccountingCleark = (IActivitySwimlane2CompartmentUIModel) diagramManager.createDiagramElement(diagram, IShapeTypeConstants.SHAPE_TYPE_ACTIVITY_SWIMLANE2_COMPARTMENT);
		cellSeattleAccountingCleark.setVerticalPartitionId(partitionSeattleHeader.getId());
		cellSeattleAccountingCleark.setHorizontalPartitionId(partitionAccountingCleakHeader.getId());
		cellSeattleAccountingCleark.setBounds(50+PARTITION_HEADER_HEIGHT, 280, 450-PARTITION_HEADER_HEIGHT, 200);
		swimlaneShape.addChild(cellSeattleAccountingCleark);
		
		// Create Send Invoice action
		IActivityAction actionSendInvoice = IModelElementFactory.instance().createActivityAction();
		swimlane.addChild(actionSendInvoice);
		actionSendInvoice.setName("Send Invoice");
		partitionSeattle.addContainedElement(actionSendInvoice);
		partitionAccountingCleark.addContainedElement(actionSendInvoice);
		IActivityActionUIModel actionShapeSendInvoice = (IActivityActionUIModel) diagramManager.createDiagramElement(diagram, actionSendInvoice);
		actionShapeSendInvoice.setBounds(180, 330, 100, 40);
		actionShapeSendInvoice.setRequestResetCaption(true);
		cellSeattleAccountingCleark.addChild(actionShapeSendInvoice);
		
		// Create control flow between Fork and Send Invoice action
		IControlFlow flowForkSendInvoice = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowForkSendInvoice);
		flowForkSendInvoice.setFrom(fork);
		flowForkSendInvoice.setTo(actionSendInvoice);
		diagramManager.createConnector(diagram, flowForkSendInvoice, forkShape, actionShapeSendInvoice, 
										new Point[] {new Point(480, 180), new Point(510, 180), new Point(510, 300), 
										new Point(150, 300), new Point(150, 350), new Point(180, 350)});
		
		// Create Invoice object node
		IObjectNode objectNode = IModelElementFactory.instance().createObjectNode();
		swimlane.addChild(objectNode);
		objectNode.setName("Invoice");
		partitionSeattle.addContainedElement(objectNode);
		partitionAccountingCleark.addContainedElement(objectNode);
		IObjectNodeUIModel shapeObjectNode = (IObjectNodeUIModel) diagramManager.createDiagramElement(diagram, objectNode);
		shapeObjectNode.setBounds(280, 400, 100, 40);
		shapeObjectNode.setRequestResetCaption(true);
		cellSeattleAccountingCleark.addChild(shapeObjectNode);
		
		// Create object flow between Send Invoice action and Invoice object
		IActivityObjectFlow flowSendInvoiceInvoice = IModelElementFactory.instance().createActivityObjectFlow();
		swimlane.addChild(flowSendInvoiceInvoice);
		flowSendInvoiceInvoice.setFrom(actionSendInvoice);
		flowSendInvoiceInvoice.setTo(objectNode);
		diagramManager.createConnector(diagram, flowSendInvoiceInvoice, actionShapeSendInvoice, shapeObjectNode, 
										new Point[] {new Point(230,370), new Point(230,420), new Point(280,420)});
		
		// Create Make Payment action
		IActivityAction actionMakePayment = IModelElementFactory.instance().createActivityAction();
		swimlane.addChild(actionMakePayment);
		actionMakePayment.setName("Make Payment");
		partitionSeattle.addContainedElement(actionMakePayment);
		partitionAccountingCleark.addContainedElement(actionMakePayment);
		IActivityActionUIModel actionShapeMakePayment = (IActivityActionUIModel) diagramManager.createDiagramElement(diagram, actionMakePayment);
		actionShapeMakePayment.setBounds(380, 330, 100, 40);
		actionShapeMakePayment.setRequestResetCaption(true);
		cellSeattleAccountingCleark.addChild(actionShapeMakePayment);
		
		// Create object flow between Invoice object and Make Payment action
		IActivityObjectFlow flowInvoiceMakePayment = IModelElementFactory.instance().createActivityObjectFlow();
		swimlane.addChild(flowInvoiceMakePayment);
		flowInvoiceMakePayment.setFrom(objectNode);
		flowInvoiceMakePayment.setTo(actionMakePayment);
		diagramManager.createConnector(diagram, flowInvoiceMakePayment, shapeObjectNode, actionShapeMakePayment, 
										new Point[] {new Point(380, 420), new Point(430,420), new Point(430,370)});
		
		// Create compartment between Reno partition and Accounting Clerk parition
		IActivitySwimlane2CompartmentUIModel cellRenoAccountingCleark = (IActivitySwimlane2CompartmentUIModel) diagramManager.createDiagramElement(diagram, IShapeTypeConstants.SHAPE_TYPE_ACTIVITY_SWIMLANE2_COMPARTMENT);
		cellRenoAccountingCleark.setVerticalPartitionId(partitionRenoHeader.getId());
		cellRenoAccountingCleark.setHorizontalPartitionId(partitionAccountingCleakHeader.getId());
		cellRenoAccountingCleark.setBounds(500, 280, 400, 200);
		swimlaneShape.addChild(cellRenoAccountingCleark);
		
		// Create Accept Payment action
		IActivityAction actionAcceptPayment = IModelElementFactory.instance().createActivityAction();
		swimlane.addChild(actionAcceptPayment);
		actionAcceptPayment.setName("Accept Payment");
		partitionReno.addContainedElement(actionAcceptPayment);
		partitionAccountingCleark.addContainedElement(actionAcceptPayment);
		IActivityActionUIModel actionShapeAcceptPayment = (IActivityActionUIModel) diagramManager.createDiagramElement(diagram, actionAcceptPayment);
		actionShapeAcceptPayment.setBounds(550, 330, 100, 40);
		actionShapeAcceptPayment.setRequestResetCaption(true);
		cellRenoAccountingCleark.addChild(actionShapeAcceptPayment);
		
		// Create control flow between Maka Payment action and Accept Payment action
		IControlFlow flowMakePaymentAcceptPayment = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowMakePaymentAcceptPayment);
		flowMakePaymentAcceptPayment.setFrom(actionMakePayment);
		flowMakePaymentAcceptPayment.setTo(actionAcceptPayment);
		diagramManager.createConnector(diagram, flowMakePaymentAcceptPayment, actionShapeMakePayment, actionShapeAcceptPayment, 
										new Point[] {new Point(480, 350), new Point(550,350)});
		
		// Create control flow betwen Accept Payment action and Join node
		IControlFlow flowAcceptPaymentJoin = IModelElementFactory.instance().createControlFlow();
		swimlane.addChild(flowAcceptPaymentJoin);
		flowAcceptPaymentJoin.setFrom(actionAcceptPayment);
		flowAcceptPaymentJoin.setTo(join);
		diagramManager.createConnector(diagram, flowAcceptPaymentJoin, actionShapeAcceptPayment, joinShape, 
										new Point[] {new Point(650, 350), new Point(670, 350), new Point(670, 250), 
										new Point(640, 250), new Point(640, 180), new Point(660, 180)});
		
		// Associate the compartment to swimlane shape
		swimlaneShape.setCompartmentIds(new String[] {
				cellSeattleOrderProcessor.getId(),
				cellRenoOrderProcessor.getId(),
				cellSeattleAccountingCleark.getId(),
				cellRenoAccountingCleark.getId()				
		});
		
		// Bring up the diagram
		diagramManager.openDiagram(diagram);
	}

	@Override
	public void update(VPAction arg0) {
		// TODO Auto-generated method stub
		
	}

}
