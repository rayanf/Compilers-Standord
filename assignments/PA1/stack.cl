(* 
 *  Programming Assignment 1
 *    Implementation of a simple stack machine.
 *
 *  Skeleton file
 *)

 
class Stack{
   data  : String  <- "";
   rank  : String <- "0";
   next  : Stack;
   init(s:String, next_node:Stack, ith: String): Stack{{
      data <- s;
      next <- next_node;
      rank <- ith;
	   self;}
   };

   pop():Stack{next};
   top():String{data};
   push(s:String):Stack{(new Stack).init(s, self, (new A2I).i2a((new A2I).a2i(rank)+1))};
   isnil():Bool{if (rank="0") then true else false fi};
};

class Main inherits IO {
   org_stack: Stack <- (new Stack);
   
   display(stack:Stack):Object{
      while (not stack.isnil()) loop{
         out_string(stack.top());
         out_string("\n");
         stack <- stack.pop();
	}pool
   };

   sumation(stack:Stack):Object{
      let fir:Int <- (new A2I).a2i(org_stack.top()) in {
         org_stack <- org_stack.pop();
         let sec:Int <-(new A2I).a2i(org_stack.top()) in {
            org_stack <- org_stack.pop();
            org_stack <- org_stack.push((new A2I).i2a(fir + sec));
         };
      }
   };

   swapping(stack:Stack):Object{
      let fir:String <- org_stack.top() in {
         org_stack <- org_stack.pop();
         let sec:String <- org_stack.top() in {
            org_stack <- org_stack.pop();

            org_stack <- org_stack.push(fir);
            org_stack <- org_stack.push(sec);
         };
      }
    };

   main():Object{
      let input: String<-"" in
         while (not input = "x") loop{
            out_string(">");
            input <- in_string();
            if (input = "d") then display(org_stack)
            else if (input = "e") then {
	    	if (org_stack.top() = "s") then {
            org_stack <- org_stack.pop();
            swapping(org_stack);}
            else if (org_stack.top() = "+") then {
               org_stack <- org_stack.pop();
               sumation(org_stack);}
            else org_stack
            fi fi;
         }else{
               org_stack <- org_stack.push(input);}
            fi fi;
         }pool
      
   };
};
