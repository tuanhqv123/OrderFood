import { useState } from 'react';
import { Search, SlidersHorizontal, Minus, Plus } from 'lucide-react';

interface Product {
  id: number;
  name: string;
  price: number;
  currency: string;
  image: string;
  variants: string[];
  category: string;
}

export const ProductList = () => {
  const [categories, setCategories] = useState([
    'Appetizers', 
    'Seafood platters', 
    'Fish', 
    'Shrimp', 
    'Crab', 
    'Squid', 
    'Rice', 
    'Drinks', 
    'Dessert'
  ]);
  
  const [activeCategory, setActiveCategory] = useState('Appetizers');
  
  const [products, setProducts] = useState<Product[]>([
    {
      id: 1,
      name: 'Spicy shrimp with rice',
      price: 70000,
      currency: 'Rp.',
      image: 'https://images.unsplash.com/photo-1601050690597-df0568f70950?q=80&w=2070&auto=format&fit=crop',
      variants: ['Original', 'Lemon zest'],
      category: 'Shrimp'
    },
    {
      id: 2,
      name: 'Garlic fried butter mussels',
      price: 75000,
      currency: 'Rp.',
      image: 'https://images.unsplash.com/photo-1632400649696-421cede44a34?q=80&w=2070&auto=format&fit=crop',
      variants: ['Original', 'Sweet chili'],
      category: 'Seafood platters'
    },
    {
      id: 3,
      name: 'Thai hot seafood soup',
      price: 90000,
      currency: 'Rp.',
      image: 'https://plus.unsplash.com/premium_photo-1671394134516-da0e0e49f7c9?q=80&w=2070&auto=format&fit=crop',
      variants: ['Tom yum', 'Creamy'],
      category: 'Seafood platters'
    },
    {
      id: 4,
      name: 'Baby corn butter prawns',
      price: 85000,
      currency: 'Rp.',
      image: 'https://images.unsplash.com/photo-1551248429-40975aa4de74?q=80&w=2090&auto=format&fit=crop',
      variants: ['Spicy', 'Mild'],
      category: 'Shrimp'
    },
    {
      id: 5,
      name: 'Seafood spaghetti marinara',
      price: 95000,
      currency: 'Rp.',
      image: 'https://images.unsplash.com/photo-1563379926898-05f4575a45d8?q=80&w=2070&auto=format&fit=crop',
      variants: ['Tomato', 'Aglio olio'],
      category: 'Seafood platters'
    },
    {
      id: 6,
      name: 'Shrimp fried spicy sauce',
      price: 82000,
      currency: 'Rp.',
      image: 'https://images.unsplash.com/photo-1625943555209-0c337a4f27f7?q=80&w=2070&auto=format&fit=crop',
      variants: ['Extra spicy', 'Medium spicy'],
      category: 'Shrimp'
    }
  ]);
  
  const [quantities, setQuantities] = useState<{[key: number]: number}>({});
  const [selectedVariants, setSelectedVariants] = useState<{[key: number]: string}>({});
  
  const handleQuantityChange = (productId: number, delta: number) => {
    setQuantities(prev => ({
      ...prev,
      [productId]: Math.max(0, (prev[productId] || 0) + delta)
    }));
  };
  
  const handleVariantSelect = (productId: number, variant: string) => {
    setSelectedVariants(prev => ({
      ...prev,
      [productId]: variant
    }));
  };
  
  const handleAddToCart = (productId: number) => {
    console.log('Added to cart:', {
      product: products.find(p => p.id === productId),
      quantity: quantities[productId] || 0,
      variant: selectedVariants[productId] || 'Original'
    });
  };

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-xl font-semibold">Product Lists</h2>
        <div className="flex gap-2">
          <button className="bg-gray-100 hover:bg-gray-200 transition-colors px-4 py-2 rounded-md text-sm">
            Input manually
          </button>
          <div className="relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={18} />
            <input 
              type="text" 
              placeholder="Search for food" 
              className="pl-10 pr-4 py-2 border border-gray-200 rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-foodpoint-primary focus:border-transparent"
            />
          </div>
          <button className="bg-gray-100 hover:bg-gray-200 transition-colors p-2 rounded-md">
            <SlidersHorizontal size={18} />
          </button>
        </div>
      </div>
      
      <div className="mb-6 overflow-x-auto">
        <div className="flex space-x-2 pb-2">
          {categories.map(category => (
            <button
              key={category}
              className={`tab-button whitespace-nowrap ${activeCategory === category ? 'active' : ''}`}
              onClick={() => setActiveCategory(category)}
            >
              {category}
            </button>
          ))}
        </div>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {products.map((product, index) => (
          <div key={product.id} className="food-card animate-fade-in" style={{ animationDelay: `${index * 0.05}s` }}>
            <img 
              src={product.image} 
              alt={product.name} 
              className="food-card-image"
            />
            <div className="p-4">
              <h3 className="font-medium mb-1">{product.name}</h3>
              <p className="text-sm text-gray-600 mb-3">{product.currency} {product.price.toLocaleString()}/serving</p>
              
              <div className="flex flex-wrap gap-2 mb-4">
                {product.variants.map(variant => (
                  <button 
                    key={variant}
                    className={`px-3 py-1 text-sm rounded-full border transition-colors ${
                      selectedVariants[product.id] === variant 
                        ? 'bg-foodpoint-primary text-white border-foodpoint-primary' 
                        : 'border-gray-300 text-gray-600 hover:bg-gray-50'
                    }`}
                    onClick={() => handleVariantSelect(product.id, variant)}
                  >
                    {variant}
                  </button>
                ))}
              </div>
              
              <div className="flex items-center justify-between">
                <div className="flex items-center space-x-2">
                  <button 
                    className="w-8 h-8 rounded-md border border-gray-300 flex items-center justify-center hover:bg-gray-100"
                    onClick={() => handleQuantityChange(product.id, -1)}
                  >
                    <Minus size={16} />
                  </button>
                  <span className="w-8 text-center">{quantities[product.id] || 0}</span>
                  <button 
                    className="w-8 h-8 rounded-md border border-gray-300 flex items-center justify-center hover:bg-gray-100"
                    onClick={() => handleQuantityChange(product.id, 1)}
                  >
                    <Plus size={16} />
                  </button>
                </div>
                
                <button 
                  className="px-4 py-2 bg-foodpoint-primary text-white rounded-md text-sm hover:bg-opacity-90 transition-colors"
                  onClick={() => handleAddToCart(product.id)}
                >
                  Add to cart
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductList;
