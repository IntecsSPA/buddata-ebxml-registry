/**
 *
 */

function submitMisc()
{
    Ext.ComponentMgr().get('miscForm').getForm().submit({url:'pippo.xml',waitingMsg:'submitting'});
    
}
