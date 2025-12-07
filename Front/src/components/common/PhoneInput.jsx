import React from 'react';
import Input from './Input';

const PhoneInput = ({ 
  label = 'Телефон', 
  name = 'phone', 
  value, 
  onChange, 
  error, 
  placeholder = '+380501234567',
  required = true 
}) => {
  const handlePhoneChange = (e) => {
    // Allow only numbers, spaces, + and - characters
    const value = e.target.value.replace(/[^0-9+\-\s]/g, '');
    e.target.value = value;
    onChange(e);
  };

  return (
    <Input
      label={label}
      name={name}
      type="tel"
      value={value}
      onChange={handlePhoneChange}
      error={error}
      placeholder={placeholder}
      required={required}
    />
  );
};

export default PhoneInput;
