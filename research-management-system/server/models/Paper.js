const mongoose = require('mongoose');

const PaperSchema = new mongoose.Schema({
  title: { 
    type: String, 
    required: [true, 'Title is required'],
    maxlength: [200, 'Title cannot exceed 200 characters']
  },
  authors: { 
    type: [String], 
    required: [true, 'At least one author is required'],
    validate: {
      validator: v => v.length > 0,
      message: 'At least one author is required'
    }
  },
  publishDate: { 
    type: Date, 
    required: [true, 'Publish date is required'] 
  },
  journal: { 
    type: String, 
    required: [true, 'Journal is required'] 
  },
  abstract: { 
    type: String,
    maxlength: [5000, 'Abstract cannot exceed 5000 characters']
  },
  keywords: {
    type: [String],
    validate: {
      validator: v => v.length <= 10,
      message: 'Cannot have more than 10 keywords'
    }
  },
  doi: {
    type: String,
    validate: {
      validator: v => !v || /^10.\d{4,9}\/[-._;()/:A-Z0-9]+$/i.test(v),
      message: 'Invalid DOI format'
    }
  },
  citations: { 
    type: Number, 
    default: 0,
    min: [0, 'Citations cannot be negative']
  }
}, { 
  timestamps: true,
  toJSON: { virtuals: true },
  toObject: { virtuals: true } 
});

module.exports = mongoose.model('Paper', PaperSchema);