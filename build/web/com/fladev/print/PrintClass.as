package com.fladev.print
{
	import flash.display.MovieClip;
	import flash.events.MouseEvent;
	import flash.printing.PrintJob;
	import flash.printing.PrintJobOptions;
		
	/**
	 * ...
	 * @author Bataglia - bataglia85@yahoo.com | www.fladev.com
	 */
	
	public class PrintClass extends MovieClip 
	{					
		private var printer:PrintJob;		
		
		public function PrintClass() {
			btn.buttonMode = true;
			btn.mouseChildren = false;
			btn.addEventListener(MouseEvent.CLICK, printFunction);
		}			
		
		private function printFunction(e:MouseEvent):void 
		{
			printer = new PrintJob();
			
			var opt:PrintJobOptions = new PrintJobOptions(true);
			//opt.printAsBitmap = true;
			
			var printerStarted:Boolean = this.printer.start();
			if (printerStarted) {
				this.printer.addPage(this.mc, null, opt);				
				this.printer.send();
				this.printer = null;
			}		
		}		
	
	}
}