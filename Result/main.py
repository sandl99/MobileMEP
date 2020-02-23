# In one cell of Jupyter Notebook
import matplotlib.pyplot as plt

# In next cell
plt.plot([25, 50, 75, 100], [9.20184, 21.4206, 33.1924, 49.9550], 'b*-', label='MBGA')   # "label" used for legend
# [<matplotlib.lines.Line2D object at ...>]   # Return a list of "Line2D" objects
plt.plot([25, 50, 75, 100], [3.2364, 9.4489, 15.8494, 12.54179], 'ro-', label='new GA')   # Another line
# [<matplotlib.lines.Line2D object at ...>]
# Set the title for the current axes
plt.title('MBGA vs new GA')
# Text(0.5,1,'My Star Plot')    # Return a "Text" object
# Set the axes labels and ranges for the current axes
plt.xlabel('Number of mobile sensor')
# <matplotlib.text.Text object at ...>   # Return a "Text" object
plt.ylabel('Exposure Value')
# <matplotlib.text.Text object at ...>
plt.axis([25, 100, 0, 100])  # [xmin, xmax, ymin, ymax]
# [1, 7, 0, 9]
# Setup legend on the current axes
plt.legend()
# <matplotlib.legend.Legend object at ...>   # Return a "Legend" object
# Save the figure to file
plt.savefig('./PlotStars.png', dpi=600, format='png')
plt.show()