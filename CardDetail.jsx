import { useState } from 'react';
import { ChevronDown, ChevronUp, Minus, Plus } from 'lucide-react';

interface CartItem {
  id: number;
  name: string;
  price: number;
  quantity: number;
  variant: string;
  addition: string;
  image: string;
}

export const CartDetails = () => {
  const [cartItems, setCartItems] = useState<CartItem[]>([
    {
      id: 1,
      name: 'Shrimp fried spicy sauce',
      price: 170000,
      quantity: 2,
      variant: 'Medium',
      addition: 'Lemon',
      image: 'https://images.unsplash.com/photo-1625943555209-0c337a4f27f7?q=80&w=2070&auto=format&fit=crop'
    },
    {
      id: 2,
      name: 'Spicy shrimp with rice',
      price: 210000,
      quantity: 3,
      variant: 'Original',
      addition: '-',
      image: 'https://images.unsplash.com/photo-1601050690597-df0568f70950?q=80&w=2070&auto=format&fit=crop'
    }
  ]);
  
  const [orderType, setOrderType] = useState<'dine-in' | 'takeaway' | 'delivery'>('dine-in');
  const [customerInfoOpen, setCustomerInfoOpen] = useState(true);
  
  const handleQuantityChange = (itemId: number, delta: number) => {
    setCartItems(prev => prev.map(item => 
      item.id === itemId 
        ? { ...item, quantity: Math.max(1, item.quantity + delta) } 
        : item
    ));
  };
  
  const subtotal = cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  const discount = subtotal * 0.1; // 10% discount
  const tax = subtotal * 0.025; // 2.5% tax
  const total = subtotal - discount + tax;

  return (
    <div className="bg-white rounded-lg shadow-card border border-gray-100 animate-fade-in">
      <div className="p-4 border-b border-gray-100">
        <div className="flex items-center justify-between">
          <h2 className="text-lg font-medium">Cart Details</h2>
          <button className="btn-icon text-gray-400 hover:text-gray-600">
            <ChevronDown size={18} />
          </button>
        </div>
      </div>
      
      <div className="p-4 border-b border-gray-100">
        <div className="flex rounded-md overflow-hidden mb-6">
          <button 
            className={`flex-1 py-2 text-center text-sm font-medium transition-colors ${orderType === 'dine-in' ? 'bg-foodpoint-primary text-white' : 'bg-gray-100 text-gray-600 hover:bg-gray-200'}`}
            onClick={() => setOrderType('dine-in')}
          >
            Dine in
          </button>
          <button 
            className={`flex-1 py-2 text-center text-sm font-medium transition-colors ${orderType === 'takeaway' ? 'bg-foodpoint-primary text-white' : 'bg-gray-100 text-gray-600 hover:bg-gray-200'}`}
            onClick={() => setOrderType('takeaway')}
          >
            Takeaway
          </button>
          <button 
            className={`flex-1 py-2 text-center text-sm font-medium transition-colors ${orderType === 'delivery' ? 'bg-foodpoint-primary text-white' : 'bg-gray-100 text-gray-600 hover:bg-gray-200'}`}
            onClick={() => setOrderType('delivery')}
          >
            Delivery
          </button>
        </div>
        
        <div className="mb-4">
          <div 
            className="flex items-center justify-between cursor-pointer"
            onClick={() => setCustomerInfoOpen(!customerInfoOpen)}
          >
            <h3 className="font-medium">Customer information</h3>
            <button className="btn-icon text-gray-400 hover:text-gray-600">
              {customerInfoOpen ? <ChevronUp size={18} /> : <ChevronDown size={18} />}
            </button>
          </div>
          
          {customerInfoOpen && (
            <div className="mt-3 space-y-4 animate-fade-in">
              <div>
                <label className="block text-sm mb-1">Customer name</label>
                <input 
                  type="text" 
                  placeholder="Enter name" 
                  className="w-full px-3 py-2 border border-gray-200 rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-foodpoint-primary focus:border-transparent"
                />
              </div>
              
              <div>
                <label className="block text-sm mb-1">Table location</label>
                <div className="relative">
                  <select className="w-full px-3 py-2 border border-gray-200 rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-foodpoint-primary focus:border-transparent appearance-none pr-10">
                    <option>Select table</option>
                    <option>Table 1A</option>
                    <option>Table 1B</option>
                    <option>Table 2A</option>
                    <option>Table 2B</option>
                  </select>
                  <ChevronDown className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 pointer-events-none" size={16} />
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
      
      <div className="p-4 border-b border-gray-100">
        <div className="flex items-center justify-between mb-3">
          <h3 className="font-medium">Order items</h3>
          <button className="text-xs text-red-500 hover:text-red-600">
            Clear all items
          </button>
        </div>
        
        <div className="space-y-4 mb-4">
          {cartItems.map(item => (
            <div key={item.id} className="flex gap-3 animate-slide-in">
              <div className="w-16 h-16 rounded-lg overflow-hidden flex-shrink-0">
                <img src={item.image} alt={item.name} className="w-full h-full object-cover" />
              </div>
              
              <div className="flex-1">
                <h4 className="font-medium">{item.name}</h4>
                <div className="text-xs text-gray-500">Variant: {item.variant}</div>
                <div className="text-xs text-gray-500">Addition: {item.addition}</div>
                
                <div className="flex justify-between items-center mt-2">
                  <div className="font-medium">Rp.{item.price.toLocaleString()}</div>
                  
                  <div className="flex items-center space-x-2">
                    <button 
                      className="w-6 h-6 rounded-md border border-gray-300 flex items-center justify-center hover:bg-gray-100"
                      onClick={() => handleQuantityChange(item.id, -1)}
                    >
                      <Minus size={14} />
                    </button>
                    <span className="w-6 text-center text-sm">{item.quantity}</span>
                    <button 
                      className="w-6 h-6 rounded-md border border-gray-300 flex items-center justify-center hover:bg-gray-100"
                      onClick={() => handleQuantityChange(item.id, 1)}
                    >
                      <Plus size={14} />
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
        
        <div className="space-y-2 text-sm pt-4 border-t border-gray-100">
          <div className="flex justify-between">
            <span className="text-gray-600">Sub total</span>
            <span>Rp. {subtotal.toLocaleString()}</span>
          </div>
          <div className="flex justify-between">
            <span className="text-gray-600">Discount (10%)</span>
            <span className="text-red-500">- Rp. {discount.toLocaleString()}</span>
          </div>
          <div className="flex justify-between">
            <span className="text-gray-600">Tax (2.5%)</span>
            <span>Rp. {tax.toLocaleString()}</span>
          </div>
          <div className="flex justify-between font-medium pt-2 border-t border-gray-100">
            <span>Total amount</span>
            <span>Rp. {total.toLocaleString()}</span>
          </div>
        </div>
      </div>
      
      <div className="p-4">
        <div className="mb-4">
          <div className="relative">
            <input 
              type="text" 
              placeholder="Enter promo code" 
              className="w-full px-3 py-2 border border-gray-200 rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-foodpoint-primary focus:border-transparent pr-20"
            />
            <button className="absolute right-1 top-1 px-3 py-1 bg-gray-100 text-sm font-medium rounded hover:bg-gray-200 transition-colors">
              Apply
            </button>
          </div>
        </div>
        
        <button className="w-full py-3 bg-foodpoint-primary text-white rounded-md font-medium hover:bg-opacity-90 transition-colors">
          Proceed payment
        </button>
      </div>
    </div>
  );
};

export default CartDetails;